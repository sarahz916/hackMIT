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
@WebServlet("/student")
public class StudentServlet extends HttpServlet {
  private final String[] STUDENT_FIELDS = {"name", "email", "meetingmedium", "location", "timezone", "subjects", "numstudents", "tutorrates", "bio"};
  //String[] STUDENT_FIELDS = {"name", "email", "college", "subjects", "time-zone", "availability"};
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    Query query = new Query("Student");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // Create arraylist of string to store students.
    ArrayList<JSONObject> students = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
        String studentJSON = "{";
        for(int i = 0; i < STUDENT_FIELDS.length; i++){
            String field = STUDENT_FIELDS[i];
            studentJSON += "\"" + field + "\"" + ":" + (String) entity.getProperty(field);
            if (i != STUDENT_FIELDS.length - 1){
                studentJSON += ",";
            }
        }
        studentJSON += "}";
        JSONObject object = new JSONObject(studentJSON);
        students.add(object);
    }

    Gson gson = new Gson();

    response.setContentType("application/json");
    response.getWriter().println(gson.toJson(students));
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
    response.sendRedirect("/index.html");
  }

}
