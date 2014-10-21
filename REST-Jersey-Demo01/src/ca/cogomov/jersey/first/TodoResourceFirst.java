package ca.cogomov.jersey.first;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/todo-first")
public class TodoResourceFirst {
  // This method is called if XMLis request
  @GET
  @Produces({ MediaType.APPLICATION_XML})
  public Todo getXML() {
    Todo todo = new Todo();
    todo.setSummary("This is the XML version");
    todo.setDescription("This is my first todo");
    return todo;
  }
  
  @GET
  @Produces({ MediaType.APPLICATION_JSON})
  public Todo getJSON() {
    Todo todo = new Todo();
    todo.setSummary("This is the JSON version");
    todo.setDescription("This is my first todo");
    return todo;
  }
  
  // This can be used to test the integration with the browser
  @GET
  @Produces({ MediaType.TEXT_XML })
  public Todo getHTML() {
    Todo todo = new Todo();
    todo.setSummary("This is my first todo");
    todo.setDescription("This is my first todo");
    return todo;
  }

} 