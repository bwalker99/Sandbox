package ca.cogomov.jersey.first;

import java.net.URI;

import org.glassfish.jersey.client.ClientConfig;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

// import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;


public class TestClient {

  public static void main(String[] args) {

	org.glassfish.jersey.client.ClientConfig config = new org.glassfish.jersey.client.ClientConfig();
	javax.ws.rs.client.Client client = javax.ws.rs.client.ClientBuilder.newClient(config);

    WebTarget target = client.target(getBaseURI());
    System.out.println("REST Jersey Demo\n\n");
    
    System.out.println("Hello");    
    System.out.println("TextPlain1: " + target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(Response.class).toString());

    System.out.println("TextPlain2: " + target.path("rest").path("hello").request().accept(MediaType.TEXT_PLAIN).get(String.class));

    System.out.println("TextXML: " + target.path("rest").path("hello").request().accept(MediaType.TEXT_XML).get(String.class));

    System.out.println("TextHTML: " + target.path("rest").path("hello").request().accept(MediaType.TEXT_HTML).get(String.class));
    
    System.out.println("\n\nTodo");    
    System.out.println("TEXT_XML: " + target.path("rest").path("todo-first").request().accept(MediaType.TEXT_XML).get(Response.class).toString());
    System.out.println("TEXT_XML: " + target.path("rest").path("todo-first").request().accept(MediaType.TEXT_XML).get(String.class));

    System.out.println("JSON-ResponseStatus: " + target.path("rest").path("todo-first").request().accept(MediaType.APPLICATION_JSON).get(Response.class).toString());
    System.out.println("JSON-Response      : " + target.path("rest").path("todo-first").request().accept(MediaType.APPLICATION_JSON).get(String.class));
    
    System.out.println("XML-ResponseStatus: " + target.path("rest").path("todo-first").request().accept(MediaType.APPLICATION_XML).get(Response.class).toString());
    System.out.println("XML-Response      : " + target.path("rest").path("todo-first").request().accept(MediaType.APPLICATION_XML).get(String.class));
    
        
    System.out.println("\n\nTodo - 2");    
    System.out.println("TEXT_XML-ResponseStatus: " + target.path("rest").path("todo-second").request().accept(MediaType.TEXT_XML).get(Response.class).toString());
    System.out.println("TEXT_XML-Response      : " + target.path("rest").path("todo-second").request().accept(MediaType.TEXT_XML).get(String.class));

    System.out.println("JSON-ResponseStatus: " + target.path("rest").path("todo-second").request().accept(MediaType.APPLICATION_JSON).get(Response.class).toString());
    System.out.println("JSON-Response      : " + target.path("rest").path("todo-second").request().accept(MediaType.APPLICATION_JSON).get(String.class));
    
    System.out.println("XML-ResponseStatus: " + target.path("rest").path("todo-second").request().accept(MediaType.APPLICATION_XML).get(Response.class).toString());
    System.out.println("XML-Response      : " + target.path("rest").path("todo-second").request().accept(MediaType.APPLICATION_XML).get(String.class));
    
 
    
    System.out.println("\n\nPredictions");    
    System.out.println("TEXT_XML: " + target.path("rest2").path("predictions/plain").request().accept(MediaType.TEXT_PLAIN).get(Response.class).toString());
    System.out.println("TEXT_XML: " + target.path("rest2").path("predictions/plain").request().accept(MediaType.TEXT_PLAIN).get(String.class));

    System.out.println("JSON-ResponseStatus: " + target.path("rest2").path("predictions/json").request().accept(MediaType.APPLICATION_JSON).get(Response.class).toString());
    System.out.println("JSON-Response:       " + target.path("rest2").path("predictions/json").request().accept(MediaType.APPLICATION_JSON).get(String.class));
    
    System.out.println("XML-ResponseStatus: " + target.path("rest2").path("predictions/xml").request().accept(MediaType.APPLICATION_XML).get(Response.class).toString());
    System.out.println("XML-Response      : " + target.path("rest2").path("predictions/xml").request().accept(MediaType.APPLICATION_XML).get(String.class));
    
  }

  private static URI getBaseURI() {

    return UriBuilder.fromUri("http://localhost:8080/REST-Jersey-Demo01").build();

  }
} 