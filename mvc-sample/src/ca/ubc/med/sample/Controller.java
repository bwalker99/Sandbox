package ca.ubc.med.sample;


/**
* Sample application.
* This application will give a simple example of using the mvc application framework for a used car dealership. 
*/
public class Controller extends ca.ubc.med.mvc.SessionController {
	
	/**
	 * Typically we will only need to define and override varialbles in the localInit method. 
	 * Here we <ul> 
	 * <li>set the APPLICATION_NAME, which is used to get properties and define the log file.</li>
	 * <li>defined the authentication type. For this sample application, we are using cookie authentication. 
	 * The cookie name is stored as a init-param to this servlet definition in the web.xml. </li>
	 * <li>and initiate the logger with the application name </li>
	 * </ul>
	 * 
	 */
	protected void localInit() {
		APPLICATION_NAME = "Sample"; 
	}
}
