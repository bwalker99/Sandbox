/*
 * Invoke the error page when all else fails.
 */
package ca.ubc.med.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorHandler extends HandlerBase {

	public void process(ServletContext sc, HttpServletRequest request,
			HttpServletResponse response) throws MVCException {}
	
	
	 public void forward (HttpServletRequest request, HttpServletResponse response) 
	    throws IOException, ServletException {
	 	
	  	String dest = SessionController.ERROR_PAGE;
	    RequestDispatcher rd  = request.getRequestDispatcher(dest);    
	    if (rd == null) {
	      throw new ServletException("dispatcher is null!");
	    }
	    rd.forward (request, response);
	  }
}
