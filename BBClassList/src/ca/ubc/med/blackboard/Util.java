package ca.ubc.med.blackboard;

import java.util.ArrayList;
import java.util.List;

import blackboard.data.user.*;
import blackboard.data.course.*;
//import blackboard.base.*;
//import blackboard.data.*;
//import blackboard.data.user.*;
//import blackboard.data.course.*;
import blackboard.persist.*;
//import blackboard.persist.user.*;
import blackboard.persist.course.*;
//import blackboard.platform.*;
import blackboard.platform.persistence.*;    
import blackboard.persist.user.UserDbLoader;

public class Util {

	public String getCoursesByUserString(Id userId) {
		String retval = "";
		try {
	    CourseDbLoader courseLoader = CourseDbLoader.Default.getInstance();
	    ArrayList<Course> courses = courseLoader.loadByUserId(userId); 
	    
	    for (int k=0 ; k < courses.size() ; k++ ) { 
	    	Course c = courses.get(k);
	    	retval += "Enrolled in: " + c.getTitle() + "(" + c.getCourseId() + ")<br/>";
	    //	getCourseRole(c.getCourseId(),userId);
	      }
		}
		catch (Exception e) { } 

		return retval;
	}

	/** 
	 * Return a list of courses associated with this user. 
	 * @param userId
	 * @return
	 */
	protected ArrayList<Course> getCoursesByUser(Id userId) {
		ArrayList<Course> courses = null;
		try {
	      CourseDbLoader courseLoader = CourseDbLoader.Default.getInstance();
	      courses = courseLoader.loadByUserId(userId); 
		}
	   	    
		catch (Exception e) { } 

		return courses;
	}

	
	
	public User getUser(String username) {
	// prefetch user data for quick lookup later
		User user = null;
	 try { 	
		 UserDbLoader userLoader = UserDbLoader.Default.getInstance();
		 user = userLoader.loadByUserName(username);
	 }
	 catch (Exception pe) { 
		 System.out.println("Error getting user object for: " + username + " " + pe.getMessage());
	 }
	 return user;
	}

	
	public String getUserCourses(String username) {
	  String retval = null;
		
	 try { 	
		 UserDbLoader userLoader = UserDbLoader.Default.getInstance();
		 User user = userLoader.loadByUserName(username);
		 Id id = user.getId();
		 retval = getCoursesByUserString(id);
	 }
	 catch (Exception pe) { 
		 System.out.println(pe.getMessage());
	 }
	 return retval;
	}
	
/** 
 * TODO - fix placeholder only	
 * @return
 */
protected String getUserCourseRole(String courseId,Id userId) { 
	return "Instructor";
}

/** 
 * TODO - fix placeholder only	
 * @return
 */
protected String getUserCourseGroup(String courseId,Id userId) { 
	return "Instructors";
}


private String getCourseRole(String courseId, Id userId) { 
	String retval = "";
	String msg = "";

    // get the membership data to determine the User's Role
	BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
	
    CourseMembership userCourseMembership = null;
    CourseMembershipDbLoader courseMembershipLoader = null;
	try {
		Id internalCourseId = bbPm.generateId(Course.DATA_TYPE,courseId); // convert String courseid to internal format.
	    courseMembershipLoader = (CourseMembershipDbLoader) bbPm.getLoader(CourseMembershipDbLoader.TYPE);
		userCourseMembership = courseMembershipLoader.loadByCourseAndUserId(internalCourseId, userId);
	} catch (KeyNotFoundException e) {
		// There is no membership record.
		msg = "There is no membership record. Better check this out:" + e;
	} catch (PersistenceException pe) {
		// There is no membership record.
		msg  = "An error occured while loading the User. Better check this out:" + pe;
	}
	CourseMembership.Role userCourseMembershipRole = userCourseMembership.getRole();
//	String userCourseMembershipRoleStr = userCourseMembershipRole.toString();
	retval = userCourseMembershipRole.toString();
	// boolean userCourseMembershipIsAvailable = userCourseMembership.getIsAvailable();

	System.out.println("\n" + retval + "<->" + msg);
	return retval;
}
	
	
	/**
	 * User Friendly String for the type of Role passed in. 
	 * @param type  Type of Role: COURSE or SYSTEM
	 * @param role  Internal role constant. ie CourseMembership.Role.GRADER
	 * @return Role as friendly string.
	 */
	static public String getRoleString(String type, Object role) {
		String uRole = "";
		if ( type.equals( "COURSE" ) ) {
			// get role based on coursemembershipRole (CourseMembership.Role)
			if( (CourseMembership.Role)role == CourseMembership.Role.COURSE_BUILDER ) {
				uRole="Course Builder";
			} else if ( (CourseMembership.Role)role == CourseMembership.Role.DEFAULT ){
				uRole="Student(Default)";
			} else if ( (CourseMembership.Role)role == CourseMembership.Role.GRADER ) {
				uRole="Grader";
			} else if ( (CourseMembership.Role)role == CourseMembership.Role.GUEST ) {
				uRole="Guest";
			} else if ( (CourseMembership.Role)role
						== CourseMembership.Role.INSTRUCTOR ) {
				uRole="Instructor";
			} else if ( (CourseMembership.Role)role == CourseMembership.Role.NONE) {
				uRole="No explicit role (NONE)";
			} else if ( (CourseMembership.Role)role == CourseMembership.Role.STUDENT) {
				uRole="Student";
			} else if ( (CourseMembership.Role)role 
					== CourseMembership.Role.TEACHING_ASSISTANT ) {
				uRole="Teaching Assistant";
			} else {
				uRole = "Cannot Identify Course Membership Role";
			}
		} else if ( type.equals( "SYSTEM" ) ) {
		    // get role based on SystemRole
			if( (User.SystemRole)role == User.SystemRole.ACCOUNT_ADMIN ) {
				uRole="Account Administrator";
			} else if ( (User.SystemRole)role == User.SystemRole.COURSE_CREATOR ) {
	         		uRole = "Course creator";
	        	} else if ( (User.SystemRole)role == User.SystemRole.COURSE_SUPPORT ) {
	          		uRole = "Course support";
	        	} else if ( (User.SystemRole)role == User.SystemRole.DEFAULT ) {
	        		uRole = "User";
	        	} else if ( (User.SystemRole)role == User.SystemRole.GUEST ) {
	        		uRole = "Guest";
	        	} else if ( (User.SystemRole)role == User.SystemRole.NONE ) {
	        		uRole = "No explicit role (NONE)";
	        	} else if ( (User.SystemRole)role == User.SystemRole.OBSERVER ) {
	        		uRole = "Observer";
	        	} else if ( (User.SystemRole)role == User.SystemRole.SYSTEM_ADMIN ) {
	        		uRole = "System Administrator";
	        	} else if ( (User.SystemRole)role == User.SystemRole.SYSTEM_SUPPORT ) {
	        		uRole = "System support";
	        	} else if ( (User.SystemRole)role == User.SystemRole.USER ) {
	        		uRole = "User";
	        	} else {
	        		uRole = "Cannot Identify User System Role";
	        	}
		} else {
			uRole = "TYPE not qualified in method.";
		}
		
		return uRole;
	}
	


}
