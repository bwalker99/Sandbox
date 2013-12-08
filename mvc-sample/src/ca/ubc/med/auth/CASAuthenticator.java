package ca.ubc.med.auth;

import javax.servlet.http.HttpServletRequest;

public class CASAuthenticator implements Authenticator {
  private String userid;
  private String username;
 
  public CASAuthenticator() {}

  public void init(HttpServletRequest request) {
	  userid = request.getRemoteUser();    // For CAS
    }
/**
 * Nothing to do as CAS has already authenticated. getRemoteUser from CAS for future use
 */
  public boolean authenticate() {	
	  
	  LDAPLook ldap = new LDAPLook("LDAP");   // LDAP config file must be named LDAP. TODO - change this???
	  if (ldap == null) { 
	   	System.out.println("Error making LDAP connection.");
	   	username = userid;
	  }
	   else {
	      username = ldap.getDisplayName(userid);
	   }	   	 
	  ldap.close();     
    return true;
  }
  

public String getUserid() { 
	return userid;
}

public String getUsername() { 
	return username;
}
}

