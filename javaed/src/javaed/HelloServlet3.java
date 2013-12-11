package javaed;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Name coming from a form
 * @author rwalker
 *
 */

public class HelloServlet3 extends HttpServlet {
	  private String name;

	  public void init() throws ServletException {
	      // Do required initialization
	      name = "NotDefined";
	  }

	  public void doGet(HttpServletRequest request,HttpServletResponse response)
	            throws ServletException, IOException	  {
		  name="NoName";	
		  // Set response content type
	      response.setContentType("text/html");

	      java.io.PrintWriter out = response.getWriter();
	      out.println("<pre>" + this.getClass().getName() + ". Output from doGet()</pre><br/>");
	  }
	  
	  public void doPost(HttpServletRequest request,HttpServletResponse response)
	            throws ServletException, IOException	  {
	      // Set response content type
		  name="NoName";
	      response.setContentType("text/html");

	      java.io.PrintWriter out = response.getWriter();
	      out.println("<pre>" + this.getClass().getName() + ". Output from doPost()</pre><br/>");
	      String temp = request.getParameter("name");
	      if (temp != null && temp.length() > 0)
	    	  name=temp;
	      out.println("<h1>Hello " + name + "</h1>");

	  }

	  
	
}
