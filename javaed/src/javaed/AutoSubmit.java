package javaed;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AutoSubmit extends HttpServlet {
	  private String name;

	  public void init() throws ServletException {
	      // Do required initialization
	      name = "NotDefined";
	  }

	  public void doGet(HttpServletRequest request,HttpServletResponse response)
	            throws ServletException, IOException	  {
		  name="NoName";
		  String tofile = "auto.jsp";
		  
		  // Set response content type
		  name="NoName";
	      String temp = request.getParameter("name");
	      if (temp != null && temp.length() > 0)
	    	  name=temp;
	      temp = request.getParameter("tofile");
	      if (temp != null && temp.length() > 0)
	    	  tofile = temp;
	      
	      request.setAttribute("nameVar", name);
	      System.out.println("AutoSubmit. Name=" + name);
	      String nextJSP = "/" + tofile;
	      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
	      dispatcher.forward(request,response);
	      
	  }
	  
	  public void doPost(HttpServletRequest request,HttpServletResponse response)
	            throws ServletException, IOException	  {
	      // Set response content type
	      
	  }

	  
	
}
