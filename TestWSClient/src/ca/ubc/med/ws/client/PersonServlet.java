package ca.ubc.med.ws.client;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import ca.ubc.med.middleware.data.*;
import ca.ubc.med.ws.service.Person;

public class PersonServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 	{
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("PersonServlet");
		ArrayList<Person> personList = null;
		Person P = new Person();

		String cwl = req.getParameter("cwl");
	
		if (cwl != null && cwl.length() > 0) {
			logger.info("looking for cwl " + cwl);		
		    P = PersonClient.getPerson(cwl);
		}
		
		String lastname = req.getParameter("lastname");
		if (lastname != null && lastname.length() > 0) {
			logger.info("looking for lastname" + lastname);		
			personList = PersonClient.getPersonList(lastname);
		}
							
		if (personList != null) 
			logger.info("Found: " + personList.size() + " person(s).");
		else 
			logger.info("No person list found");
		
		req.setAttribute("personList", personList);
		req.setAttribute("person", P);
			
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher( "/person.jsp" );
	    if (dispatcher == null) {
	    	throw new ServletException ("RequestDispatcher is  null!");
	    }

		dispatcher.forward(req, resp);
	}
}
