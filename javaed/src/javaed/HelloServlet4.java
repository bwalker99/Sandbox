package javaed;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Name coming from a form. Going to a jsp.
 * @author rwalker
 *
 */

public class HelloServlet4 extends HttpServlet {
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
	      String temp = request.getParameter("name");
	      if (temp != null && temp.length() > 0)
	    	  name=temp;
	      
	      request.setAttribute("nameVar", name);
	      System.out.println("forwarding to jsp");
	      String nextJSP = "/nameOutput.jsp";
	      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
	      dispatcher.forward(request,response);
	      
	  }

	  
	
}
