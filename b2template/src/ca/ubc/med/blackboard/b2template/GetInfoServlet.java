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


/**
 * Process the request from the jsp page 'start.jsp'. 
 * Note that we are processing both Get and Post with the same code. 
 * @author rwalker
 *
 */
public class GetInfoServlet extends HttpServlet{

	private static final Logger LOGGER = LoggerFactory.getLogger( GetInfoServlet.class );
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
		  String message = "";
		  
		  // Get the BB logged in username as passed to us by the calling page.
		  String username = request.getParameter("username");
		  LOGGER.debug("Getting information for: {}",username);
		  
		  // Get the userdata field as passed to us by the calling page.
		  String userdata = request.getParameter("userdata");
		  LOGGER.debug("Userdata passed in: {}",userdata);
		  
		  // Create a BB User object for this user.
		  User user = util.getUser(username);
		  if (user == null) { 
			  LOGGER.error("Error getting user object: null");
			  message = "Error getting user object: null";
		  }
		  else 
			  LOGGER.debug("Got user object: {}",user.getUserName());  
		  
		  // Get a list of BB courses the user is enrolled in.
		  // this uses the supplied Blackboard bean:  blackboard.data.course.Course;
		  
		  ArrayList<Course> courses = new ArrayList<Course>();
		  if (user != null)
			  courses = util.getCoursesByUser(user.getId());
		  
		  // Create a blank list of MyBbCourse objects.
		  // This is our own defined course bean: ca.ubc.med.blackboard.b2template.data.MyBbCourse
		  // Our own course bean has some of the Blackboard info, plus the user's course role in that course.
		  
		  ArrayList<MyBbCourse> myCourses = new ArrayList<MyBbCourse>();
		  
		  // Go through all the users BB Course and create a myBbCourses object from each course.
		  // Added value. Add the user's role in the course to our object. 
		  for (int k=0 ; k < courses.size() ; k++ ) { 
		    	Course c = courses.get(k);
		    	
		    	MyBbCourse myc = new MyBbCourse();
		    	myc.setDescription(setNoneIfBlank(c.getDescription()));
		    	myc.setCourseId(c.getCourseId());
		    	myc.setCourseName(c.getDisplayTitle());
		    	myc.setRoleIdentifier(util.getUserCourseRole(c.getId(), user.getId()));
		    	myc.setRole(util.getCourseRoleString(myc.getRoleIdentifier()));
		    	
		    	myCourses.add(myc);
		    	LOGGER.debug("Coursename/ID/Description: {} {} {}",c.getDisplayTitle(),c.getCourseId(),c.getDescription());
		    }
		  
		  // This passes the BB User object the the List of MyBbCourse objects to the showinfo.jsp page.
		  request.setAttribute("user",user);
		  request.setAttribute("mycourses",myCourses);
		  request.setAttribute("userdata",userdata.toUpperCase());  // pass back user data upshifted, as an example.
		  request.setAttribute("message",message);
		  
		  // Get Last login date as a string. It's an ugly format in the User object. 
		  // Probably a better way of doing this.
		  request.setAttribute("lastlogin", user.getLastLoginDate().getTime().toString());
		   
		  // Forward (pass control) to the jsp page using the concept of 'java servlet forwarding'
		  // In MVC speak, this class is the 'controller' and is now passing control to the 'view'
		  RequestDispatcher rd  = request.getRequestDispatcher("showinfo.jsp");    
		    if (rd == null) {
		      throw new ServletException ("RequestDispatcher is null!");
		    }
		    rd.forward (request, response);
	  }
	  
	/**
	 *  Set a parameter to *none* if it is blank. bbNG Inventory List does not like blank fields...
	 * @param s
	 * @return
	 */
	private String setNoneIfBlank(String s) {
		String retval = "*none*";
		if (s != null && s.length() > 0)
			retval = s;
		return retval;
	}
}
