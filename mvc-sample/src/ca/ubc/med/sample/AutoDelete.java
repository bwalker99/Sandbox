package ca.ubc.med.sample;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.ubc.med.mvc.ItemHelper;
import ca.ubc.med.mvc.MVCException;
import java.sql.*;

/**
 * MVC Sample Application<br/>
 * Delete servlet for Auto. <br/>
 * Typically one servlet for updating of each object type.<br/> 
 * Note that the Helper class does all the work. 
 */
public class AutoDelete extends  ca.ubc.med.mvc.HandlerBase {

	public void process (ServletContext sc, 
            HttpServletRequest request, HttpServletResponse response)
	throws MVCException {
	
	logger.debug("Auto Delete");
	
	 java.sql.Connection conn = null;
	 ItemHelper IH;
	
		// Get index number of record. If -1, assume this is a new record. 
		String id = null;
		id = request.getParameter("id");
		int int_index = -1;
		if (id != null) {
			  try {  int_index = Integer.parseInt(id); }
			  catch (NumberFormatException nfe) { 
				 throw new MVCException("ID  must be supplied.",nfe);  // TODO Message should come from elsewhere. 
		 	     }
			 }
		
	try { 
	    IH = (ItemHelper) Class.forName("ca.ubc.med.sample.data.AutoHelper").newInstance();
	 }
	 catch (Exception e) { 
		 throw new MVCException(e);
	 }
	
	  try {
		  conn = ca.ubc.med.sql.JNDI.getConnection("SAMPLE");
		  if (conn == null) 
			  throw new MVCException("Could not make database connection..");
		  
		  IH.setConn(conn);
		  IH.setLogger(logger);
		  IH.delete(int_index);
		  info.setMessage("Deleted auto with id: " + int_index);
		  logger.info("Deleted auto with id: " + int_index);		  
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

	}
}

