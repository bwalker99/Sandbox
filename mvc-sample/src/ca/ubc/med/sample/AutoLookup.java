package ca.ubc.med.sample;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.ubc.med.mvc.ItemHelper;
import ca.ubc.med.mvc.MVCException;
import java.sql.*;

import ca.ubc.med.sample.data.*;

/**
 * The NNNNLookup typically provides a lookup and returns an object for one value.<br/> 
 * Uses a NNNNHelper class to actually do the work. <br/>
 * Typically retrieves an object using the supplied database id value using the Helper and puts it on the request object as a javabean  for display by a JSP page.<br/><br/> 
 * 
 * In this sample application, information for a single Auto is returned. 
 */

public class AutoLookup extends  ca.ubc.med.mvc.HandlerBase {

	public void process (ServletContext sc, 
            HttpServletRequest request, HttpServletResponse response)
	throws MVCException {
	
	logger.debug("AutoLookup");
	
	 java.sql.Connection conn = null;
	 ItemHelper IH;
	
	 int id;   
	 try { 
		 id = Integer.parseInt(request.getParameter("id"));
	 }
	 catch (NumberFormatException nfe) { 
		 throw new MVCException("Auto ID must be provided.",nfe);  // TODO Message should come from elsewhere. 
	 }
	 
	 try { 
	    IH = (ItemHelper) Class.forName("ca.ubc.med.sample.data.AutoHelper").newInstance();
	 }
	 catch (Exception e) { 
		 throw new MVCException(e);
	 } 
	 Auto A = new Auto(); 
	 request.setAttribute("Auto",A);   // put blank one in case we bail out, OR this is new.
	 
	 if (id != -1) {    // Creating new record.
	     try {
		  conn = ca.ubc.med.sql.JNDI.getConnection("SAMPLE");
		  if (conn == null) 
			  throw new MVCException("Error connecting to database.");
		  
		  IH.setConn(conn);
		  A = (Auto) IH.get(id);
		  
		  if (A.getId() == -1)
			  throw new MVCException("Auto ID(" + id + ") does not exist.");
		  
		  logger.debug(A.toString());
		 
	  }
	  catch (MVCException e) { 
		  throw new MVCException(e);
	  }
	  finally { 
		  if (conn != null) { 
		     try {    conn.close(); }
		     catch (SQLException e) { } 
		     conn = null;
		   }
	  }	  
	   request.setAttribute("Auto",A);		
	}
 }   // End of create new.
}
