package ca.ubc.med.ws.data;

import java.sql.*;
import java.util.Vector;

import ca.ubc.med.middleware.data.Person;

/**
 * @author bwalker
 *
 */
public class PersonLookup {
	private final static String DATABASE="WS";
//	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("PERSON");
			
	/**
	 * Look up people based on pidm OR email.  If both are specified, search by pidm ONLY.
	 * @param pidm
	 * @param email
	 * @return a vector of Person objects that meet the criteria
	 */
	public Vector<Person> getPersonList(String identifier) {
		Vector<Person> personList = new Vector<Person>();

		Person p1 = new Person();
		p1.setFirstname("Joe");p1.setLastname("Clark");
		personList.add(p1);
		
		Person p2 = new Person();
		p2.setFirstname("Pierre");p2.setLastname("Trudeau");
		personList.add(p2);
		
		return personList;
	}
	
	public Person getPerson(String identifier) {
		Person p = new Person();
		p.setCwl(identifier);
		return p;
		}

	/**
	 * This should be used for testing purpose only.
	 * @param args
	 */
	public  static void main(String[] args) 	{
		
		System.out.println("Testing webservice client");
		PersonLookup PL = new PersonLookup();
		
		// test person lookup
	    Vector<Person> personList = PL.getPersonList("no-op");
		
 		for (Person p : personList)
			System.out.println(p.getFirstname() + " " + p.getLastname());

		System.out.println("DONE");
	}

	
}

