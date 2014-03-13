package ca.ubc.med.ws.demo.data;

//import java.sql.*;
import java.util.Vector;

import ca.ubc.med.ws.middleware.data.Person;

/**
 * @author bwalker
 *
 */
public class PersonLookup {
	private final static String DATABASE="WS";
//	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("PERSON");
			
	/**
	 * Look up people based last name. 
	 * EXAMPLE ONLY : Typically look up from a database. 
	 * @param lastname of list of users to lookup
	 * @return a vector of Person objects that meet the criteria
	 */
	public Vector<Person> getPersonList(String lastname) {
		Vector<Person> personList = new Vector<Person>();

		Person p = new Person();
		p.setFirstname("Joe");p.setLastname("Clark");p.setCwl(lastname);
		personList.add(p);
		
		p = new Person();
		p.setFirstname("Christy");p.setLastname("Clark");p.setCwl(lastname);
		personList.add(p);
		
		p = new Person();
		p.setFirstname("Josephine");p.setLastname("Clark");p.setCwl(lastname);
		personList.add(p);
		
		return personList;
	}
	
	/**
	 * EXAMPLE ONLY : Typically look up from a database.
	 * @param identifier What to lookup
	 * @return Person object 
	 */
	public Person getPerson(String identifier) {
		Person p = new Person();
		p.setFirstname("Pierre"); p.setLastname("Petroleum");
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

