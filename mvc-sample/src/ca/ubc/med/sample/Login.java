package ca.ubc.med.sample;
/** 
 * Login to the FoM Sample
 * Supports multiple types of authentication, defined in the properties file.
 * IF login is successful, creates a session object which is checked by the controller
 * before every call for validation.   
 */
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.ubc.med.auth.Authenticator;
import ca.ubc.med.mvc.Info;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Login  extends HttpServlet {
	Authenticator auth;
	Logger log = org.apache.log4j.Logger.getLogger("Sample Login");

		
public void init () throws ServletException {
	// Get Authenticator from init file. Set to default if not provided and create an instance of it. 
	HashMap<String,String> props = getProperties();
	String authClass = props.get("authenticator");
	if (authClass == null)  // default if not defined. 
		authClass = "ca.ubc.med.auth.SimpleAuthenticator";

	log.debug("Using authenticator:" + authClass);
	
	try { 
		   auth = (Authenticator) Class.forName(authClass).newInstance();
		} catch (Exception e) { 
			throw new ServletException("Authenticator class could not be initialized",e);
		}	
	 }
	 
 public void doGet (HttpServletRequest request, HttpServletResponse response) 
       throws ServletException, IOException  {
    doPost(request,response);
   }

public void doPost (HttpServletRequest request, HttpServletResponse response) 
throws ServletException, IOException  {
    
	// The UserInfo object contains information about the user's session. 
	ca.ubc.med.auth.UserInfo userinfo;
	
	javax.servlet.http.HttpSession session = request.getSession();
    userinfo = (ca.ubc.med.auth.UserInfo)session.getAttribute(session.getId());
    
    if (userinfo == null) {    // Not logged in. Do so now.... 
	  auth.init(request);      // Initialize authentication object.
	  
	  // TODO - Provide nicer error message
	  if (!auth.authenticate())  // authenticate
		 throw new ServletException("You do not have access to this application.");
		
	  // populate the user object 
	   userinfo = new ca.ubc.med.auth.UserInfo(auth.getUserid(),auth.getUsername(),request);
 
	   // and set it as session object. 
       session.setAttribute(session.getId(),userinfo);
       session.setMaxInactiveInterval(3600 * 6); // Try this. 6 hours
    
       log.debug("Login for " + userinfo.getUserid());
    }
    else {
    	log.debug("User already logged in" + userinfo.getUserid());
    	userinfo.setValues(request);
    }
	 
    log.debug(userinfo.toString());
    Info info = new Info();
    info.setUsername(userinfo.getUsername());
    request.setAttribute("Info", info);
    String dest = "controller?action=list";
        
	RequestDispatcher rd  = request.getRequestDispatcher(dest);    
    if (rd == null) {
      throw new ServletException ("RequestDispatcher is null!");
    }
    rd.forward (request, response);	
  }	


private  HashMap<String,String> getProperties() { 
  HashMap<String,String> props = new HashMap<String,String>();
  ResourceBundle bundle = ResourceBundle.getBundle ("SampleParms");
   Enumeration e = bundle.getKeys();
   while (e.hasMoreElements()) {
     String key = (String) e.nextElement(); 
     String value = bundle.getString(key);
     props.put(key,value);
   }
 return props;
 }

}
	

