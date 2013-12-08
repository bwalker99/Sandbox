package ca.ubc.med.sample;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import ca.ubc.med.mvc.*;
import ca.ubc.med.sample.data.Auto;

/**
 * NNNNLister classes typically provide an array (or itemlist) of java bean objects to be returned to the viewer.<br/> 
 * Typically have a Lister for each type of object. <br/>
 * This servlet returns a list autos.<br/>
 * Note that the logger variable is initialized in the Controller before this method is called so we can safely use it.<br/> 
 */
public class AutoLister extends  ca.ubc.med.mvc.HandlerBase {
	
	public void process (ServletContext sc, 
            HttpServletRequest request, HttpServletResponse response)
	throws MVCException {

   logger.debug("AutoLister");
   System.out.println(this.getClass().getName() + ":AutoLister");
   
	String sql = " SELECT * FROM sample_autos order by 1 ";  
	
	// Note. Muist create and controll database connections explicitly in these Handler functions. Don't let them go unclosed. 
	java.sql.Connection conn = null;
	 ItemHelper IH;
	 try { 
	    IH = (ItemHelper) Class.forName("ca.ubc.med.sample.data.AutoHelper").newInstance();  // or could use a factory. 	    
	 }
	 catch (Exception e) { 
		 throw new MVCException("Error creating AutoHelper", e);
	 }
	 
	// This should get rid of 'Generics' warning message, but doesn't seem to.  
	java.util.Vector<Object> V = new java.util.Vector();  // Create a Vector to store a list of Auto bean objects.

	
	logger.debug("AutoLister-2");
	  try {
		  conn = ca.ubc.med.sql.JNDI.getConnection("SAMPLE");  // Can create a connection for each application or just use the default by leaving the parameter blank.

		  if (conn == null)    		 
		     throw new MVCException("Error connecting to database.");
	  
		  logger.debug("AutoLister-3");
		  IH.setConn(conn);
		  IH.setLogger(logger);
		  PreparedStatement ps = conn.prepareStatement(sql);
		  logger.debug("AutoLister-4");
		  ResultSet rs = ps.executeQuery();
		  while (rs.next()) {
			  // This is the authorized method of doing it. The one the teach you in school. However this takes NumRows + 1 database reads and used two cursors. 
			  
			  logger.debug("Adding info for: " + rs.getInt("ID")); 
			  V.add(IH.get(rs.getInt("ID")));
			  
			  // This is the quick tricky way of doing. Requires one database read and one cursor. Don't need the Helper.  
			  //  Auto A = new Auto(rs);
			  //   V.add(A);			  
		  }
		  rs.close();
		  ps.close();
	   }
	  catch (MVCException me) { 
		  throw new MVCException(me);
	  }  	  	  
	  catch (SQLException e) { 
		  logger.error("Error reading  database:" + e.getMessage());
		  throw new MVCException(e);
	  }
	  finally { 
		  if ( conn != null) {
		    try {    conn.close(); }
		    catch (SQLException e) { } 
		    conn = null;
	    }
	 }

// Put the list of autos in an ItemList object on the Request object to be processed by the jsp view.	  
   request.setAttribute("ItemList",new ItemList(V));
	   
	}
}
