package ca.ubc.med.auth;

import javax.servlet.http.HttpServletRequest;


/**
 * Authenticate to an LDAP store. If successful set a cookie with the ID and username in the cookie. 
 * Bob Walker, Jane Yi.  Langara College. May 2005.
 * This cookie can then be used to authorize other transactions from any web process.
 * This class encrypts the userid and username simple crypt mechanism. 
 * You can user org.it4bc.auth.Cypher.decrypt to decrypt the values if you need to.
 *  
 * Update: 
 * Oct 2007, Jane Yi - added option to  authenticate user to a desired LDAP host by:
 *									 1. login as a bind user with given binding attribute and password first, 
 * 								 2. find the binding attribute of the user to be authenticated by a given attribute such as pdsloginid from the LDAP,
 * 								 3. try to bind to the host with the user's binding attribute and password
 * Nov 2011 Added to WebBB application  
 */
public class LDAPAuthenticator  implements Authenticator {
  private String userid;
  private String username;
  javax.servlet.http.HttpServletRequest request;
 
  public void init(HttpServletRequest request) {
	this.request = request;    
  }

/**
 * The main authentication method. 
 * Checks to see if LDAP authentication works for this user. 
 */
  public boolean authenticate() {
	  boolean retval = false;
  
	  userid = request.getParameter("userid");
	  String password = request.getParameter("password");
	  LDAPLook ldap = new LDAPLook("LDAP");   // LDAP config file must be named LDAP. TODO - change this???
	  if (ldap == null) { 
	   	System.out.println("Error making LDAP connection.");
	  }
	   else {
	      retval = ldap.checkAuthentication(userid, password);
	      username = ldap.getDisplayName(userid);
	   }	   	 
	  ldap.close();     
      return retval;
  } 

  
  public String getUserid() { 
		return userid;
	}
  
  public String getUsername() { 
	  return username;
  }
   
}

