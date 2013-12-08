package ca.ubc.med.auth;

/**
 * Simple Authenticator. Passes if userid = password. 
 * @author bob
 *
 */
public class SimpleAuthenticator implements Authenticator {
  private String userid;
  private String password;
  private String username;
  javax.servlet.http.HttpServletRequest request;
  
  public SimpleAuthenticator() {}

  public void init(javax.servlet.http.HttpServletRequest request) {
	  this.request = request;
  }

  public boolean authenticate() {
    userid = request.getParameter("userid");
    password = request.getParameter("password");
    username = request.getParameter("username");
    
    if (username == null || username.length() == 0)
    	username = userid;

    if (userid == null || password == null)
    	return false;
    if (userid.equalsIgnoreCase(password))
    	return true;
    else
    	return false;
  }
  
  public String getUserid() { 
	  return userid;
  }
  public String getUsername() { 
	  return username;
  }
}

