/**
 * Part of the mvc Development environment
 * Provides generic and basic application wide information. 
 * This object will be placed on the request object by the Controller for all jsp to access
 * It will also be available to all Handlers for retrieval and setting of information. 
 */
package ca.ubc.med.mvc;

import java.util.HashMap;


public class Info {
private String message;
private String username;

public Info() { 
	message = null;   // General messages to pass back to the client
	username = null;
}
	
/**
 *  Message can only be displayed once.
 */
public String getMessage() { 
	String temp = message;
	message = null;
	return temp; 
	}

public String getUsername() { 
	return username;
}

public void setMessage(String s) { message = s; }
public void resetMessage() {  message = null; }
public void setUsername(String s) { username = s; }

}