package ca.ubc.med.ws.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.WebServiceRef;

import ca.ubc.med.ws.service.*;
//import ca.ubc.med.middleware.data.*;

/**
 * Note that we are using the wsimport generated version of the Person object:
 * ca.ubc.med.ws.service.Person, NOT the user supplied one: 
 * ca.ubc.med.middleware.data.Person;
 * @author Bob
 *
 */
public class PersonClient {
	@WebServiceRef
	private static PersonServiceService service = new PersonServiceService();
	
	public static Person getPerson(String identifier) {
		Person P = new Person();
		
		if (service != null) {
			PersonService port = service.getPersonServicePort();
			
			if (port != null) {
				P = port.getPerson(identifier);
			}
		}
		return P;
	}
	
	public static ArrayList<Person> getPersonList(String lastname) {
		ArrayList<Person> personList = null;
		
		if (service != null) {
			PersonService port = service.getPersonServicePort();
			
			if (port != null) {
				personList =  (ArrayList<Person>) port.getPersonList(lastname);
			}
		}
			
		return personList;
	}
	
	/**
	 * Test client from command line. 
	 * java ca/ubc/med/ws/client/PersonClient
	 */
	public static void main(String[] args) {
		
		PersonService port = service.getPersonServicePort();
		
		System.out.println("\nSubject lookup test. Service=" + service);
		
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
