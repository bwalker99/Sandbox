package ca.ubc.med.ws.demo.client;


import javax.xml.ws.BindingProvider;

import java.util.List;
import java.util.Map;

import ca.ubc.med.ws.demo.service.Person;
import ca.ubc.med.ws.demo.service.PersonService;
import ca.ubc.med.ws.demo.service.PersonServiceService;


/** 
 * ExternalInterface Client to Web Services. <br/>
 * This client establishes methods for calling FoMDIM Web Services that can be used by external programs. <br/>
 * The external programs are expected to provide the Web Services host base URL and a passkey.<br/><br/> 
 * This class is used in the MedicolUserManagement Building Block to retrieve fomidm information to be used in Blackboard. 
 * 
 * @author rwalker
 *
 */
public class DemoTester {
	
private  String endpoint;
private  String endpoint_norm = "http://fomvss269d4:8080/fomws";
private  String endpoint_ssl = "http://fomvss269d4:8080/fomws";

PersonService port;
PersonServiceService service;


public DemoTester(String endpoint) {
	this.endpoint = endpoint;
}

public void setEndpoint(String endpoint) {
	this.endpoint = endpoint;
}


public void init() { 
	service = new PersonServiceService();
	port = service.getPersonServicePort();

	Map<String, Object> req_ctx = ((BindingProvider) port).getRequestContext();		
	req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint + "/person");
	
}
	

/**
 * Test client from command line. 
 * java ca/ubc/med/ws/client/PersonClient
 */

public static void main(String[] args) {
	

	
    String endpoint;
    String endpoint_norm = "http://localhost:8080/TestWSServer/person";
    String endpoint_ssl = "https://localhost:8443/TestWSServer/person";
    
    endpoint = endpoint_norm;
    if (args.length > 0 && args[0].equalsIgnoreCase("SSL") )
    	endpoint = endpoint_ssl;

	PersonServiceService service = new PersonServiceService();
	PersonService port = service.getPersonServicePort();

	Map<String, Object> req_ctx = ((BindingProvider) port).getRequestContext();		
		
	req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
				
	System.out.println("\nSearch for Person. WS Host: " + endpoint);
	
	String identifier = "medinst1";
	String lastname = "clarklist";
					
	System.out.println("\nSearch for Single User:");
	Person person = port.getPerson(identifier);
	if (person != null) {
		System.out.println(person .getCwl() + ":" + person.getFirstname() + " " + person.getLastname());
	}
	else {
		System.out.println("Person not found");
	}
	
	List<Person> AL = port.getPersonList(lastname);
	
	System.out.println("\nSearch for List of Users:");
	for (Person p : AL) 
		System.out.println(p .getCwl() + ":" + p.getFirstname() + " " + p.getLastname());
	
	
}


}
