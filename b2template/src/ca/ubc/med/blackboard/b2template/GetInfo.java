package ca.ubc.med.blackboard.b2template;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import blackboard.data.user.*;
import blackboard.data.course.*;
import ca.ubc.med.blackboard.b2template.data.*;


public class GetInfo extends HttpServlet{

	private static final Logger LOGGER = LoggerFactory.getLogger( GetInfo.class );
	public void init() throws ServletException { }

	  public void doGet (
	                     HttpServletRequest request, 
	                     HttpServletResponse response) 
	    throws ServletException, IOException  {
	    processRequest(request, response);
	  }

	  public void doPost (
	                      HttpServletRequest request, 
	                      HttpServletResponse response) 
	    throws ServletException, IOException  {
	  	processRequest(request,response);
	  }

	  
	  private void processRequest(HttpServletRequest request, 
		        HttpServletResponse response) throws ServletException,IOException { 		  
		    
		  // Create and instance of our util object for use later. 
		  Util util = new Util();
		  
		  // Get the BB logged in username as passed to us by the calling page.
		  String username = request.getParameter("username");
		  LOGGER.debug("Getting information for: {}",username);
		  
		  // Create a BB User object for this user.
		  User user = util.getUser(username);
		  
		  // Get a list of BB courses the user is enrolled in. 
		  ArrayList<Course> courses = util.getCoursesByUser(user.getId());
		  
		  // Create a blank list of MyBbCourse objects.
		  ArrayList<MyBbCourse> myCourses = new ArrayList<MyBbCourse>();
		  
		  // Go through all the users BB Course and create a myBbCourses object from each course.
		  // Added value. Add the user's role in the course to our object. 
		  for (int k=0 ; k < courses.size() ; k++ ) { 
		    	Course c = courses.get(k);
		    	
		    	MyBbCourse myc = new MyBbCourse();
		    	myc.setDescription(c.getDescription());
		    	myc.setCourseId(c.getCourseId());
		    	myc.setCourseName(c.getDisplayTitle());
		    	myc.setRoleIdentifier(util.getUserCourseRole(c.getId(), user.getId()));
		    	
		    	myCourses.add(myc);
		    	LOGGER.debug("Coursename/ID: {} {}",c.getDisplayTitle(),c.getCourseId());
		    }
		  
		  // This passes the BB User object the the List of MyBbCourse objects to the showinfo.jsp page.
		  request.setAttribute("user",user);
		  request.setAttribute("mycourses",myCourses);
		   
		  // Forward (pass control) to the jsp page using the concept of 'java servlet forwarding'
		  RequestDispatcher rd  = request.getRequestDispatcher("showinfo.jsp");    
		    if (rd == null) {
		      throw new ServletException ("RequestDispatcher is null!");
		    }
		    rd.forward (request, response);
	  }
	  
	
}
