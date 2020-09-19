import com.google.appengine.api.datastore.*;
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
  private final String[] TUTOR_FIELDS = {"name", "email", "meeting-medium", "location", "time-zone", "subjects", "num-students", "tutor-rates", "bio"};
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Query query = new Query("Tutor");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // Create arraylist of string to store tutors.
    ArrayList<JSONObject> tutors = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
        String tutorJSON = "{";
        for(int i = 0; i < TUTOR_FIELDS.length; i++){
            String field = TUTOR_FIELDS[i];
            tutorJSON += "\"" + field + "\"" + ":" + (String) entity.getProperty(field);
            if (i != TUTOR_FIELDS.length - 1){
                tutorJSON += ",";
            }
        }
        tutorJSON += "}";
        JSONObject object = new JSONObject(tutorJSON);
        tutors.add(object);
    }

    Gson gson = new Gson();

    response.setContentType("application/json");
    response.getWriter().println(gson.toJson(tutors));
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
    response.sendRedirect("/index.html");
  }

}
