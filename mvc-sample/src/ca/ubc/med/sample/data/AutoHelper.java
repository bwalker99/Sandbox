package ca.ubc.med.sample.data;
/**
 * Sample application. Example of a Helper class that works with a java bean<br/>
 * Each bean (int this case, Auto.java) has a helper that reads, saves, and deletes the associated record from the database. 
 * These 'helpers' can be put into a Factory and/or constructed on the fly based on naming conventions. <br/><br/>
 * 
 * The underlying database tables are expected to have a RubyOnRails type of layout. That is, an identifying PK id number field.<br/>
 * The 'save' method can extract information from any object, as that is what is passed in. In a servlet environment, it is typically a servlet request object,
 *  and the information is extracted from a html form. 
 */

import ca.ubc.med.mvc.MVCException;
import java.sql.*;

public class AutoHelper extends ca.ubc.med.mvc.ItemHelper {
	
	 /**
	  * Get info for one auto
	  * @param index The database PK of the auto you want.
	  */
	public  Object get(int index)  throws MVCException {
		String sql = "select * from sample_autos where id = ?";	
		Auto A = new Auto();		
		try {			
			PreparedStatement ps = conn.prepareStatement(sql);			
			ps.setInt(1,index);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) { 
				A.setId(rs.getInt("id"));				
				A.setMake(rs.getString("make"));
				A.setModel(rs.getString("model"));				
				A.setColour(rs.getString("colour"));
				A.setCost(rs.getInt("cost"));				
				// Alternate method. 
				// A = new Auto(rs);
			}			
		rs.close();
		ps.close();
		
		} catch (SQLException e) { 
			logger.error("Error reading database: " + e.getMessage());
			throw new MVCException(e);
		}
		
		return A; // Return the bean containing the data. 
	} 
	
	/**
	 * Save auto info based on data in html form.
	 * @param O An object that holds the data to be saved. Typically a html servlet request object, 
	 * @return An object that represents the data just saved. 
	 */
	public  Object save(Object O)   throws MVCException { 
		
		// The object to get data from could be anything. In this case it is a Servlet Request Object
		javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest)O;
	
		// Get index number of record. If -1, assume this is a new record. 
		String id = null;
		id = request.getParameter("id");
		int int_index = -1;
		boolean exists = false;
	    
		if (id != null) {
		  try {  int_index = Integer.parseInt(id); }
		  catch (NumberFormatException nfe) { 
			 throw new MVCException("ID  must be supplied.",nfe);  // TODO Message should come from elsewhere. 
	 	     }
		 }
	 	
		// to see if the index of an existing record is passed in for updating, if not, we are saving NEW information
		if (int_index > 0) 
			exists = true;
	    message = "";

	    // Using resultset, as we can treat most of the fields for updates and inserts the same way, 
		try {
			conn.setAutoCommit(false);    // for mysql
			Statement st1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet RS = st1.executeQuery("SELECT sample_autos.*  FROM sample_autos where id = " + id );	        	        
	
	        if (exists) {
	        	RS.next();      	
	         }
	        else {	        	
	        	RS.moveToInsertRow();
	        	// RS.updateInt("id", int_index);   	        	
	        }
	       
	        RS.updateString("make",request.getParameter("make"));
	        RS.updateString("model",request.getParameter("model"));	        
	        RS.updateString("colour",request.getParameter("colour"));
	        
	        
	        String temp=  request.getParameter("cost");	        
	        int cost = 0;
	        if (temp != null) 
	        {
				// take the value that the user has entered, round to the nearest dollar and convert to cents
	        	cost = (Math.round(Float.parseFloat(temp))*100);
	        	//cost = Integer.parseInt(temp);   	
	        }
	         RS.updateInt("cost",cost);
	        
	        if (exists) 
	        	RS.updateRow();
	        else  
	        	RS.insertRow();	        

	        // Assumption. If this is a new record, the ID will be assigned by a database trigger. Must retrieve it here. 
		    if (!exists) { 
		      ResultSet RSind = st1.executeQuery("SELECT max(id) id FROM sample_autos"); // This is safe, as have not yet committed, so database is locked.
		    	if (RSind != null) {
		      	   RSind.next();
		       	   int_index = RSind.getInt("id");   // Get new value assigned by database	     
		       	   RSind.close();
		        }
		    }	        
		    
	        conn.commit();
	        RS.close(); 
	        st1.close();
	        
		} catch (SQLException e) {
		    	throw new MVCException(e);  
		}
		
   // The save function returns what it just saved as an object.		
	 return get(int_index);		

	}

/**
 * delete the request record.
 */	
	public  void delete(int index)   throws MVCException { 
    try {
		  PreparedStatement ps = conn.prepareStatement("Delete from sample_autos where id = ?");      
  		  ps.setInt(1,index);
		  ps.executeUpdate();
		  conn.commit();
		  ps.close();
	    }
	  catch (SQLException e) {
	    	throw new MVCException(e);
	   }
   }	
}