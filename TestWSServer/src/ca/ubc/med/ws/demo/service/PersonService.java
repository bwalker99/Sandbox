package ca.ubc.med.ws.demo.service;

import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import ca.ubc.med.ws.demo.data.PersonLookup;
import ca.ubc.med.ws.middleware.data.Person;  // shared object

/**
 * The Web Service for Employee Information. Uses PersonLookup to do all the work.
 * @author bwalker
 *
 */
@WebService
public class PersonService {
		
	PersonLookup personlookup = new PersonLookup();
		
	@WebMethod
	public Vector<Person> getPersonList(@WebParam(name="identifier")String identifier) {
		return personlookup.getPersonList(identifier);
	}
		
	@WebMethod
	public Person getPerson(@WebParam(name="identifier")String identifier) {
		return personlookup.getPerson(identifier);

	}
	
	public static void main(String[] args) {
		PersonService ps = new PersonService();
				
		System.out.println("Getting person List");
		Vector<Person> personList = ps.getPersonList("someone");
		
		for (Person p : personList)
			System.out.println(p.getFirstname() + " " + p.getLastname());
			
		System.out.println("Getting single person");			
		Person p = ps.getPerson("Someone");
		System.out.println(p.getFirstname() + " " + p.getLastname());
	}
	 
}