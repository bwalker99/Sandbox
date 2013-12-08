package ca.ubc.med.mvc;

import java.io.IOException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HandlerBase.
 * All servlets descend from this class. 
 * The main controller servlet calls the process() method
 * Usually the process method is overwridden to provide the functionality desired.
 * Bob Walker, Nov 2004
 * 
 * Aug 2007 BW 
 * Copied from ca.langara.common to here. 
 * The main difference with this version is there is no common Connection object that inherited classes can use.
 * This proved to be problematic as the convenience functions setConn and releaseConn were too easy to misuse, leaving connections open.  
 * All descendants are now expected to create and manage their own Connection objects. <br/><br/>
 * Dec 2009 JP added localdebug field so that the classes in ca.langara.banner can use MVC
 */
public abstract class HandlerBase {
 protected String override_dest = null;
 protected org.apache.log4j.Logger  logger;
 protected Info info;  // The general purpose Info object.
  /**
   * Over ride this class to provide functionality.
   * @param sc
   * @param request
   * @param response
   * @throws IOException
   * @throws ServletException
   */
  public abstract void process (ServletContext sc, 
                       HttpServletRequest request, HttpServletResponse response) 
    throws MVCException;

  /**
   * Forward to desired JSP or other servlet.
   *  Three ways to decide how to forward:<br/> 
   *   1) if override_dest is set, use this. This is typically set by an ancestor class to override any other indication.<br/> 
   *   2) if request parameter 'dispatchto' is defined, use this. Destination determined by calling page.<br/>
   *   3) Get the destination url (typically jsp page) from the Url.properties file based on action. <br/>
   *   4) All else fails, go to error.jsp.<br/>
   * @param request
   * @param response
   * @throws IOException
   * @throws ServletException
   */
  public void forward (HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {

	String dest = null;
	String dispatchto = request.getParameter("dispatchto");
	String action = request.getParameter("action");
	if (action == null) 
		  logger.error("forward:Action is not defined");  
	
	if (override_dest != null) {
		dest = override_dest; 
		override_dest = null;  // reset for next call.
	   }
	else  if (dispatchto != null && dispatchto.length() > 0 )
		dest = dispatchto;
	else
  	  dest = ca.ubc.med.mvc.DispatchUrls.getUrl(request.getParameter("action")); 
	
	if (dest== null)  {
	     logger.error("forward:Destination page is not defined"); 
		 dest = SessionController.ERROR_PAGE;
        }
	
        logger.debug(this.getClass().getName() + ":forward:action=" + request.getParameter("action") + ":dest=" + dest);
    
    RequestDispatcher rd  = request.getRequestDispatcher(dest);    
    if (rd == null) {
      throw new ServletException ("RequestDispatcher is null!");
    }
    rd.forward (request, response);
  }
  
 /**
  * Set the logger and info objects. This method is always called by the Controller before process() is called. Descendant classes have full access to these objects. 
  * @param l The logger object
  * @param i The info object
  */
public void init(Logger l, Info i) { 
	  logger = l;
	  info = i;
 } 
	
}
