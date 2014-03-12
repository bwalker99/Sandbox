package ca.ubc.med.ws.server;

import javax.xml.ws.Endpoint;
import ca.ubc.med.ws.service.PersonService;

/**
 * Don't need this. Use for standalone use. Replaced by Tomcat implementation. 
 * @author Bob
 *
 */
public class Publisher {
	public static void main(String[] args) {
		System.out.println("Person Service Publisher is now available...");
		Endpoint.publish("http://localhost:8181/TestWSServer/person", new PersonService());
	}
}
