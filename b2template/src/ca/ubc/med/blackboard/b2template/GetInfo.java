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
import ca.ubc.med.blackboard.b2template.data.MyUser;


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
		    
		  Util util = new Util();
		  String username = request.getParameter("username");
		  
		  LOGGER.debug("Getting information for: {}",username);
		  User user = util.getUser(username);
		  
		  MyUser myuser = new MyUser();
		  myuser.setFirstname(user.getGivenName());
		  myuser.setLastname(user.getFamilyName());
		  myuser.setUsername(user.getUserName());
		  myuser.setEmail(user.getEmailAddress());
	
		  ArrayList<Course> courses = util.getCoursesByUser(user.getId());
		  for (int k=0 ; k < courses.size() ; k++ ) { 
		    	Course c = courses.get(k);
		    	LOGGER.debug("Coursename/ID: {} {}",c.getDisplayTitle(),c.getCourseId());
		    }
		  
		  request.setAttribute("user",user);
		  request.setAttribute("myuser",myuser);
		  request.setAttribute("courses",courses);
		   
		  RequestDispatcher rd  = request.getRequestDispatcher("showinfo.jsp");    
		    if (rd == null) {
		      throw new ServletException ("RequestDispatcher is null!");
		    }
		    rd.forward (request, response);
	  }
	  
	
}
