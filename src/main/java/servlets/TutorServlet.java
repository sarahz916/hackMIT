import com.google.appengine.api.datastore.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

/** Servlet that returns Tutor data */
@WebServlet("/tutor")
public class TutorServlet extends HttpServlet {
     
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
     
    //int maxTutors = getNumDisplayTutors(request);
     // Retrieves Tutor data from datastore. 
     // Displays most recent Tutors first.
    Query query = new Query("Tutor");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    //create a tutor data class?
    // Create arraylist of string to store Tutors.
    ArrayList<String> tutors = new ArrayList<>();
    for (Entity entity : results.asList(FetchOptions.Builder.withLimit(10))) {
      String name = (String) entity.getProperty("name");
      String text = (String) entity.getProperty("text");
      tutors.add(name + ": " + text + "\n");  
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(tutors));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //enumeration name and value into datastore
    String name = request.getParameter("name");
    String text = request.getParameter("text-input");
    long timestamp = System.currentTimeMillis();
    Entity TutorEntity = new Entity("Tutor");
    TutorEntity.setProperty("name", name);
    TutorEntity.setProperty("text", text);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(TutorEntity);
    response.sendRedirect("/index.html");
  }

}
