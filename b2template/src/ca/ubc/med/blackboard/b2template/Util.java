package ca.ubc.med.blackboard.b2template;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import blackboard.data.course.CourseMembership;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.platform.persistence.PersistenceServiceFactory;
import blackboard.data.user.*;
import blackboard.data.course.*;
import blackboard.persist.user.*;
import blackboard.persist.course.*;

public class Util {
	
	private static final Logger LOGGER = LoggerFactory.getLogger( GetInfoServlet.class );
	public static String VERSION = "1.0.1";       // Handy.
	
	/**
	 * Get a BB User object based on the lms username
	 * @param username The logged in LMS username (note. At UBC, for non-students, this is probably lower-case puid)
	 * @return A BB User Object
	 */
	public User getUser(String username) {
		User user = null;
		if (username == null)
			return user;
	 try { 	
		 UserDbLoader userLoader = UserDbLoader.Default.getInstance();
		 user = userLoader.loadByUserName(username);
	 }
	 catch (Exception pe) { 
		 LOGGER.error("Error getting user object for: {}",username,pe);
	 }
	 return user;
	}


	
	/** 
	 * Return a list of BB courses associated with this user. 
	 * @param userId
	 * @return List of Course objects. 
	 */
	protected ArrayList<Course> getCoursesByUser(Id userId) {
		System.out.println("Getting courses by userid: " + userId);
		ArrayList<Course> courses = null;
		try {
	      CourseDbLoader courseLoader = CourseDbLoader.Default.getInstance();
	      courses = courseLoader.loadByUserId(userId); 
		}
	   	    
		catch (Exception e) {
			LOGGER.error("Error getting courses for user: {}",userId,e);
		} 

		return courses;
	}

 
	
	
	/** 
	 * Get the user's Course Role. Returned as a nice String. 
	 * @param courseId The Course pk ID
	 * @param userId The User ID
	 * @return User's course role as a String. eg 'Instructor'. 
	 */
	public String getUserCourseRole(Id courseId, Id userId) { 
		String retval = "";

		BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
		
	    CourseMembership userCourseMembership = null;
	    CourseMembershipDbLoader courseMembershipLoader = null;
		try {
		    courseMembershipLoader = (CourseMembershipDbLoader) bbPm.getLoader(CourseMembershipDbLoader.TYPE);
			userCourseMembership = courseMembershipLoader.loadByCourseAndUserId(courseId, userId);
		} catch (KeyNotFoundException e) {
			LOGGER.error("No course role information",e);
		} catch (PersistenceException pe) {
			LOGGER.error("No course role information",pe);
		}
		
		CourseMembership.Role userCourseMembershipRole = userCourseMembership.getRole();
		retval = userCourseMembershipRole.getIdentifier();

		return retval;
	}

	/**
	 * User Friendly String for the type of Course Role passed in. 
	 * @param type The CourseMembership.Role.getIdentifier() value
	 * @return Role as friendly string. For example: Instructor, Auditor
	 */
	public String getCourseRoleString(String type) {
		String uRole = "*unknown*";
			// get role based on coursemembershipRole (CourseMembership.Role)
			if(type.equals("B")) uRole="Course Builder";
			else if(type.equals("G"))	uRole="Grader";
			else if(type.equals("U"))	uRole="Guest";
			else if(type.equals("P"))	uRole="Primary Instructor";		// Note: Our own interpretation.
			else if(type.equals("S")) 	uRole="Student";
			else if(type.equals("T"))	uRole="Teaching Assistant";
			else if(type.equals("UBC_Secondary_Instructor")) uRole = "Instructor";	// Note: Our own interpretation
			else if(type.equals("UBC_Sec_Instructor")) uRole = "Instructor";	// Note: Our own interpretation
			else if(type.equals("UBC_Auditor")) 	uRole = "Auditor";
			else if(type.equals("UBC_Grader")) 	uRole = "Grader";
			return uRole;
		}	
	

}
