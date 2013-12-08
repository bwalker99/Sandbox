package ca.ubc.med.sample;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.ubc.med.mvc.ItemHelper;
import ca.ubc.med.mvc.MVCException;
import java.sql.*;

import ca.ubc.med.sample.data.Auto;

/**
 * MVC Sample Application <br/>
 * Update servlet for Auto.  <br/>
 * Typically one servelt for updating of each object type. <br/> 
 * Note that the Helper class does all the work. 
 */
public class AutoUpdate extends  ca.ubc.med.mvc.HandlerBase {

	public void process (ServletContext sc, 
            HttpServletRequest request, HttpServletResponse response)
	throws MVCException {
	
	logger.debug("Auto Update");
	
	 java.sql.Connection conn = null;
	 ItemHelper IH;
	
	 	 try { 
	    IH = (ItemHelper) Class.forName("ca.ubc.med.sample.data.AutoHelper").newInstance();
	 }
	 catch (Exception e) { 
		 throw new MVCException(e);
	 }
	 
	 Auto A = new Auto();

	 request.setAttribute("Auto",A);   // put blank one incase we bail out.
	  try {
		  conn = ca.ubc.med.sql.JNDI.getConnection("SAMPLE");
		  if (conn == null) 
			  throw new MVCException("Could not make database connection..");
		  
		  IH.setConn(conn);
		  IH.setLogger(logger);
		  A = (Auto) IH.save(request);
		  info.setMessage( "Saved: " + A.toString());
		  logger.info(" Saved : " + A.toString());
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
	   request.setAttribute("Auto",A);		// Reset the bean with the real object.
	}

}

