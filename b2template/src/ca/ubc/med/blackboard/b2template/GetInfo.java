package ca.ubc.med.blackboard.b2template;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

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
		    
		  
		  String username = request.getParameter("username");
		  
		  LOGGER.debug("Getting information for: {}",username);
		  String retval = "";
		  try { 
			  retval =  sync.processCourse(courseSync,studentGroup, wshost, passkey);
		  }
		  catch (Exception e) { 
			  LOGGER.error("Error processing course :" + e.getMessage());
			  retval = "Processing error :" + e.getMessage();
		  }
		  
		  request.setAttribute("studentGroup",studentGroup);
		  request.setAttribute("courseSync",courseSync);		  
		  request.setAttribute("result",retval); 		  
		   
		    RequestDispatcher rd  = request.getRequestDispatcher("studentGroupResult.jsp");    
		    if (rd == null) {
		      throw new ServletException ("RequestDispatcher is null!");
		    }
		    rd.forward (request, response);
	  }
	  
}
