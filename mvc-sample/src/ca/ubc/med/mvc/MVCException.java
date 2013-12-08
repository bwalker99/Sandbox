package ca.ubc.med.mvc;
import java.io.*;
/**
 * Exception to be used for MVC application. 
 * TODO: add logger element. 
 */
public class MVCException extends Exception {
	private String localmessage = null;

	protected org.apache.log4j.Logger  logger = org.apache.log4j.Logger.getLogger(SessionController.APPLICATION_NAME);
	
	public MVCException(Throwable exception) {
	// TODO exception.printStackTrace(); ???
	 super(exception);
	 localmessage =  exception.getMessage();
	  System.out.println(new java.util.Date() + ":" + exception.getMessage());
	  logger.error(exception.getMessage());	  
	  exception.printStackTrace(System.err);

	}

  public MVCException(String message,Throwable exception) {
  super(message,exception);
  this.localmessage = message;
  System.out.println(new java.util.Date() + ":" + localmessage + ":" + exception.getMessage());
  logger.error( localmessage + ":" +  exception.getMessage());
  exception.printStackTrace(System.err);
  }

  public MVCException(String message) {
	  super(message);
	  this.localmessage = message;
	  System.out.println(new java.util.Date() + ":" + localmessage);
	  logger.error(localmessage);
	  }

  public String getLocalizedMessage() { return localmessage;  }
}
