import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import org.json.JSONObject;  

/** Servlet that returns comment data */
@WebServlet("/tutor")
public class TutorServlet extends HttpServlet {
    private final String[] STUDENT_FIELDS = {"name", "email", "meeting-medium", "location", "time-zone", "subjects", "num-students", "tutor-rates", "bio"};
    private final String[] TUTOR_FIELDS = {"name", "email", "meeting-medium", "location", "time-zone", "subjects", "num-students", "tutor-rates", "bio"};
    private final String[] FILTER_FIELDS = {"name", "email", "meeting-medium", "location", "time-zone", "subjects", "num-students", "tutor-rates", "bio"};
    private final int NUM_OF_MATCHES = 3;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<JSONObject> tutors = getTutors();
        JSONObject student = getStudent(request);
        ArrayList<JSONObject> matchedTutors = filter(student, tutors); 
        Gson gson = new Gson();

        response.setContentType("application/json");
        response.getWriter().println(gson.toJson(matchedTutors));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Entity TutorEntity = new Entity("Tutor");
        //Enumeration fields = request.getParameterNames();
        for (String currField: TUTOR_FIELDS){
            String value = request.getParameter(currField);
            TutorEntity.setProperty(currField, value);
        }
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(TutorEntity);
        response.sendRedirect("/form_submission.html");
    }

    private ArrayList<JSONObject> getTutors(){
        Query query = new Query("Tutor");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        // Create arraylist of string to store tutors.
        ArrayList<JSONObject> tutors = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            String tutorJSON = "{";
            for(int i = 0; i < TUTOR_FIELDS.length; i++){
                String field = TUTOR_FIELDS[i];
                String stored = (String) entity.getProperty(field);
                if (!(stored.equals(""))){
                    tutorJSON += "\"" + field + "\"" + ":" + (String) entity.getProperty(field);
                    if (i != TUTOR_FIELDS.length - 1){
                        tutorJSON += ",";
                    }
                }

            }
            tutorJSON += "}";
            JSONObject object = new JSONObject(tutorJSON);
            tutors.add(object);
        }
        return tutors;
    }

    private JSONObject getStudent(HttpServletRequest req){
        String email = req.getUserPrincipal().getName();
        Filter propertyFilter =
            new FilterPredicate("email", FilterOperator.EQUAL, email);
        Query query = new Query("Student").setFilter(propertyFilter);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        // Create arraylist of string to store tutors.
        ArrayList<JSONObject> tutors = new ArrayList<>(); 
        String tutorJSON = "{";
        for (Entity entity : results.asIterable()) {
           
            for(int i = 0; i < TUTOR_FIELDS.length; i++){
                String field = TUTOR_FIELDS[i];
                tutorJSON += "\"" + field + "\"" + ":" + (String) entity.getProperty(field);
                if (i != TUTOR_FIELDS.length - 1){
                    tutorJSON += ",";
                }
            }
            tutorJSON += "}";
            break;
        }
        return  new JSONObject(tutorJSON);
    }

    public ArrayList<JSONObject> filter(JSONObject student, ArrayList<JSONObject> tutorlist){
        ArrayList<JSONObject> SimilarTutors = new ArrayList<>();
        for (int i = 0; i < tutorlist.size(); i ++){
            int check = 0;
            for (String prop: FILTER_FIELDS){
                if (student.get(prop).equals(tutorlist.get(i).get(prop))){
                    check ++;
                }

            }                
            if (check >= NUM_OF_MATCHES){
                    SimilarTutors.add(tutorlist.get(i));
            }
        }
        return SimilarTutors;
    }
}