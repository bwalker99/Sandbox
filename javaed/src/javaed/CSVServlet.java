package javaed;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class CSVServlet extends HttpServlet {
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
	      
		  doCSV(request,response);
	      response.setContentType("text/html");
	      java.io.PrintWriter out = response.getWriter();
	      out.println("<pre>" + this.getClass().getName() + ". Output from doPost. File written.</pre><br/>");
	      out.flush();
	      
	  }

	  
	  
	  
	  private void doCSV(HttpServletRequest request, HttpServletResponse response)	  {
	      response.setContentType("text/csv");
	      response.setHeader("Content-Disposition", "attachment; filename=\"userOutput.csv\"");
	      try
	      {
	          java.io.OutputStream outputStream = response.getOutputStream();
	    
	          String outputResult = "Column 1,Column 2,Column 3,Column 4,Column 5,Column 6,Column 7,Column 8,Column 9,Column 10\n";
	          outputStream.write(outputResult.getBytes());

	          outputResult = "xxxx,yyyy,zzzz,aaaa,bbbb,ccccc,dddd,eeee,ffff,gggg\n";
	          outputStream.write(outputResult.getBytes());
	          
	          outputResult = "111,222,333,444,555,666,777,888,999,101010\n";
	          outputStream.write(outputResult.getBytes());

	          outputStream.flush();
	          outputStream.close();
	      }
	      catch(Exception e)
	      {
	          System.out.println(e.toString());
	      }
	  }
	
}
