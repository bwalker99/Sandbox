package ca.ubc.med.auth;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * Authenticate from an LTI Tool consumer. 
 *   
 */
public class LTIAuthenticator  implements Authenticator {
  private String userid;
  private String username;
  javax.servlet.http.HttpServletRequest request;
  private String mysecret = null; 
  private String forward = null;
  
  private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("LTIAuthenticate");
 
  public void init(HttpServletRequest request) {
	this.request = request;    
	
	HashMap<String,String> props = getProperties();

	forward = props.get("app.forwardto"); 
	if (forward == null) 
		forward = "index.html";
		
	mysecret = props.get("lti.sharedsecret");
	if (mysecret == null) 
		mysecret = "Manuj";	
	else 
		mysecret = mysecret.trim();

  }

/**
 * The main authentication method. 
 * Checks to see if LDAP authentication works for this user. 
 */
  public boolean authenticate() {
	  boolean retval = false;
	  
	  
	  blackboard.blti.message.BLTIMessage msg = blackboard.blti.provider.BLTIProvider.getMessage( request );
	  String key = msg.getKey();
	  // log.debug("Key from provider = |" + key + "| Local key=|" + mysecret + "|");
		  
	  if ( blackboard.blti.provider.BLTIProvider.isValid( msg, mysecret ) ) { // That's it! Authenticated.
		  log.debug("Successfully authenticated using LTI...");
		  retval = true;
		  
		  // TODO - check the following two...
		  username = request.getParameter("username");
		  userid = request.getParameter("user_id");
		  if (username == null)
			  username = userid;

		  showLTIParams(msg);
	  }
  
      return retval;
  } 

  
  public String getUserid() { 
		return userid;
	}
  
  public String getUsername() { 
	  return username;
  }
  
  /**
   * Get properties from properties file. 
   * @return
   */
  	public static HashMap<String,String> getProperties() { 
  		HashMap<String,String> props = new HashMap<String,String>();
  		ResourceBundle bundle = ResourceBundle.getBundle ("fomidmParms");
  		Enumeration<String> e = bundle.getKeys();
  		while (e.hasMoreElements()) {
  			String key =  e.nextElement(); 
  			String value = bundle.getString(key);
  			props.put(key,value);
  		}
  		return props;
  	}
 
private void showLTIParams(blackboard.blti.message.BLTIMessage msg) { 
	

    Map<String,String> customParams = msg.getCustomParameters();
	  
    String values="";
    String delimiter="";
	    for (Map.Entry<String,String> entry : customParams.entrySet()) {
	    	values += delimiter + entry.getKey() + "=" + entry.getValue();
	    	log.debug("Custom Param: " + entry.getKey() + "=" + entry.getValue());
	    }
	    
    log.debug("System Params:");
	Enumeration<String> E = request.getParameterNames();
	while (E.hasMoreElements()) { 
	      String param = E.nextElement();
	      log.debug(param + "=" + request.getParameter(param));
	      }
 }  
}

