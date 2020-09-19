package servlets;

import com.google.appengine.api.datastore.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

/** Servlet that returns comment data */
@WebServlet("/tutor")
public class TutorServlet extends HttpServlet {
     
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
     
    //int maxComments = getNumDisplayComments(request);
     // Retrieves comment data from datastore. 
     // Displays most recent comments first.
    Query query = new Query("Comment");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // Create arraylist of string to store comments.
    ArrayList<String> comments = new ArrayList<>();
    for (Entity entity : results.asList(FetchOptions.Builder.withLimit(10))) {
      String name = (String) entity.getProperty("name");
      String text = (String) entity.getProperty("text");
      comments.add(name + ": " + text + "\n");  
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String name = request.getParameter("name");
    String text = request.getParameter("text-input");
    long timestamp = System.currentTimeMillis();
    Entity CommentEntity = new Entity("Comment");
    CommentEntity.setProperty("name", name);
    CommentEntity.setProperty("text", text);
    CommentEntity.setProperty("timestamp", timestamp);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(CommentEntity);
    response.sendRedirect("/index.html");
  }

}
