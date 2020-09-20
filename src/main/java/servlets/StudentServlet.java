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
@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private final String[] STUDENT_FIELDS = {"name", "email", "meetingmedium", "location", "time-zone", "subjects", "num-students", "tutor-rates", "bio"};
    private final String[] TUTOR_FIELDS = {"name", "email", "meetingmedium", "location", "time-zone", "subjects", "num-students", "tutor-rates", "bio"};
    private final String[] FILTER_FIELDS = {"name", "email", "meetingmedium", "location", "time-zone", "subjects", "num-students", "tutor-rates", "bio"};
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<JSONObject> students = getStudents();
        JSONObject student = getStudent(request);
        ArrayList<JSONObject> matchedStudents = filter(student, students); 

        Gson gson = new Gson();

        response.setContentType("application/json");
        response.getWriter().println(gson.toJson(matchedStudents));
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Entity StudentEntity = new Entity("Student");
        //Enumeration fields = request.getParameterNames();
        for (String currField: STUDENT_FIELDS){
            String value = request.getParameter(currField);
            StudentEntity.setProperty(currField, value);
        }
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(StudentEntity);
        response.sendRedirect("/form_submission.html");
    }
    private ArrayList<JSONObject> getStudents(){
        Query query = new Query("Student");

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
                if (student.get(prop) == tutorlist.get(i).get(prop)){
                    check ++;
                }
            }
        }
        return SimilarTutors;
    }

}
