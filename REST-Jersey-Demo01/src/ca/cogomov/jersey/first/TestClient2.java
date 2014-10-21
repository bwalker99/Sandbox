package ca.cogomov.jersey.first;


import java.net.URI;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import javax.ws.rs.client.*; 


/*
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

import de.vogella.jersey.todo.model.Todo;
*/

public class TestClient2 {
	

  public static void main(String[] args) {
	  
	org.glassfish.jersey.client.ClientConfig config = new org.glassfish.jersey.client.ClientConfig();
	javax.ws.rs.client.Client client = javax.ws.rs.client.ClientBuilder.newClient(config);

    WebTarget target = client.target(getBaseURI());

	   System.out.println("Jersey Test 2\n\n"); 
	  
//    ClientConfig config = new DefaultClientConfig();
//    Client client = Client.create(config);
//    WebResource service = client.resource(getBaseURI());
    // create one todo
//    Todo todo = new Todo("3", "Blabla");
            
//    ClientResponse response = service.path("rest").path("todos")
  //      .path(todo.getId()).accept(MediaType.APPLICATION_XML)
  //      .put(ClientResponse.class, todo);
    // Return code should be 201 == created resource
   // System.out.println(response.getStatus());
    // Get the Todos
    System.out.println("List all: " + target.path("rest").path("todo-second").request()
        .accept(MediaType.TEXT_XML).get(String.class));
    // Get JSON for application
//    System.out.println(target.path("rest").path("todo-second").request()
//        .accept(MediaType.APPLICATION_JSON).get(String.class));
    // Get XML for application
//    System.out.println("Count: " + target.path("rest").path("todo-second/count").request()
//        .accept(MediaType.TEXT_XML).get(String.class));

    // Get the Todo with id 1
    System.out.println("List first: " + target.path("rest").path("todo-second/1").request()
        .accept(MediaType.TEXT_XML).get(String.class));
    // get Todo with id 1
    target.path("rest").path("todos/1").request().delete();
    // Get the all todos, id 1 should be deleted
    System.out.println("LIst all after delete: " + target.path("rest").path("todo-second").request()
        .accept(MediaType.APPLICATION_XML).get(String.class));

    // create a Todo
    /*
    Form form = new Form();
    form.add("id", "4");
    form.add("summary", "Demonstration of the client lib for forms");
    response = service.path("rest").path("todos")
        .type(MediaType.APPLICATION_FORM_URLENCODED)
        .post(ClientResponse.class, form);
    
    System.out.println("Form response " + response.getEntity(String.class));
    // Get the all todos, id 4 should be created
    System.out.println(service.path("rest").path("todos")
        .accept(MediaType.APPLICATION_XML).get(String.class));
        
        */

  }

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost:8080/REST-Jersey-Demo01").build();
  }
}