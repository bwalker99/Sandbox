package ca.ubc.med.mvc;
 


import org.apache.log4j.Logger;
import java.io.IOException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SessionController extends HttpServlet {
  public static final String ERROR_EVENT    = "ERROR_EVENT";
  public static final String UNKNOWN_EVENT  = "UNKNOWN_EVENT";
  public static final String EVENT            = "action";	
  public static final String ERROR_PAGE = "error.jsp";
  public  static String APPLICATION_NAME = "*changeme*";   // usually overridden in descendant class. 
  
  protected HashMap events = new HashMap(); // List of events.
  protected HashMap<String,String> access = new HashMap<String,String>(); // Access to events by role.
  
  protected org.apache.log4j.Logger  log;
  protected Info info;   // The general purpose info object
  
  public void init () throws ServletException {
    // Create the info object that can be used by the entire application. 
    info = new Info();

    localInit();  // Typically descendent controller will override this method. Sets the application name.
      
    // create the Log4jLog object using the application name just set.
    setLog();   
  
    
    // get the event values and save them into events
    ResourceBundle bundle = null;
    try { 
       bundle = ResourceBundle.getBundle (APPLICATION_NAME);
      }
       catch (java.util.MissingResourceException mre) { 
    	   log.fatal("Can't get actions from properties file: " + mre.getMessage());
    	   throw new ServletException("Can't get actions from properties file. Can't start servlet. ");
       }
    

    Enumeration e = bundle.getKeys();
    log.info("init. Loading events...");
    while (e.hasMoreElements()) {
      String key = (String) e.nextElement(); 
      String value = bundle.getString(key);
    
      log.info(key + "=" + value);
      try {
        HandlerBase event = (HandlerBase) Class.forName(value).newInstance();
        if (!events.containsValue(event))
           events.put (key, event);
        } catch (Exception exc) {
        log.fatal(":event:" + key + "  NO HANDLER FOUND! " + value);  
      }
    }
    
// Load URLS into static class for all to access    
// TODO - This may not be right. One instance for the entire JVM!    
   DispatchUrls U = DispatchUrls.getInstance();
   U.load(APPLICATION_NAME + "-Urls");

  }

  /**
   * Define application name and authentication method for application. 
   * Typically this is the only method of Controller that is overwridden in a descendant. 
   *
   */
protected void localInit() { 
	APPLICATION_NAME = "**CHANGEME**"; 
}


/**
 * create a Log4jLog to be used by this application
 * Should be overriden by the subclass in every application
 */
protected void setLog() { 
	log = org.apache.log4j.Logger.getLogger(APPLICATION_NAME);
	log.debug("Enabled log4j logging for application: " + APPLICATION_NAME);
}

  public void doGet (
                     HttpServletRequest request, 
                     HttpServletResponse response) 
    throws ServletException, IOException  {
    processRequest(request, response);
  }

  public void doPost (
                      HttpServletRequest request, 
                      HttpServletResponse response) 
    throws ServletException, IOException  {
  	processRequest(request,response);
  }
  
 /**
  * This is where all the work is done. Invoke the requested event and return control to the specified URL
  * @param request HttpRequest
  * @param response
  * @throws ServletException
  * @throws IOException
  */ 
 private void processRequest(HttpServletRequest request, 
        HttpServletResponse response) throws ServletException,IOException { 	
	 
	ca.ubc.med.auth.UserInfo userInfo; // The object stored as a session object. 
    
	 String event = null;
    event = validateEvent(request);
    log.debug(":processRequest action=" + event );
    
	HttpSession session = request.getSession(true);
    userInfo = (ca.ubc.med.auth.UserInfo)session.getAttribute(session.getId());
    
    if (userInfo == null) { 
        log.error("Error getting UserInfo object");
        MVCException E = new MVCException("Authentication. You must be logged in to access this page.");       
        request.setAttribute("error", E);
        event = ERROR_EVENT;    	
    }
    else {
       log.debug("User:" + userInfo.getUserid() + " Admin? " + userInfo.getAdmin());
       if (!validateAccess(event) )    {
          MVCException E = new MVCException("Authorization failed. You do not have access to this page.");       
          request.setAttribute("error", E);
          event = ERROR_EVENT;
       }
    }
    
    HandlerBase handler = getHandler (event);
    handler.init(log,info);
    
     log.debug(":processRequest action=" + event + " Handler=" + handler.getClass().getName());  
    
    try { 
     handler.process (getServletContext(), request, response);
      } 
    catch (MVCException e) {
     log.error("handler.process threw exception.");
      request.setAttribute("error", e);
	  handler = getHandler(ERROR_EVENT);
	  if (e != null)
		  log.error(e.getMessage());
      }
    info.setUsername(userInfo.getUsername());
    request.setAttribute("Info", info);
  
   if (handler == null)
	  log.error(":controller:Handler is null!!!!");
   else 
      handler.forward (request, response);
  }

 /**
  * Verify that requested action event in the list 
  * @param request
  * @return the name of the event to be processed.
  */
  protected String validateEvent (HttpServletRequest request) {
    String e = request.getParameter(EVENT);
    if (e == null || !events.containsKey(e)) { 
      e = UNKNOWN_EVENT;
    }
    return e;
  }
  
  /**
   * Validate that the user has access to this action. 
   * Actions are stored with roles in the 'access' array. 
   * Get the action and check if the user has the role associated with the action.
   * *** Dummy for now - may want to add roles later. We'll see *** 
   * @param event The event or 'action' that is being performed. 
   * @return True if user is allowed access to this action, false if not.
   */
  private boolean validateAccess(String event) {	  
	//  boolean retval = false;
//	  String role = access.get(event);
//	  log.debug("ValidateAccess: Checking for event: " + event + " user needs " + role + " role");
//	  if (role != null) 
//		  retval = userInfo.getRole().equals(role);
	  return true; // retval;	  
  }
/**
 * Returns the java class associated with the requested event.
 * @param e
 * @return
 */
  protected HandlerBase getHandler (String e) {
    HandlerBase h;
    try {
      h = (HandlerBase) events.get(e);
    } catch (Exception exc) {
      h = (HandlerBase) events.get(UNKNOWN_EVENT);
    }
    return h;
  }

}
