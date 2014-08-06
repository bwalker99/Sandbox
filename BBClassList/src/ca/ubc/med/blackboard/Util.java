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
import blackboard.data.course.CourseMembership.Role;
import blackboard.persist.course.CourseMembershipDbLoader;

import blackboard.persist.role.PortalRoleDbLoader;
import blackboard.data.role.*;


public class Util {

	/** 
	 * Return a list of courses as an html String for a give user
	 * @param userId BB User Object
	 * @return String
	 */
	public String getCoursesByUserString(Id userId) {
		String retval = "";
		
		try {
	    CourseDbLoader courseLoader = CourseDbLoader.Default.getInstance();
	    CourseMembershipDbLoader courseMembershipLoader = CourseMembershipDbLoader.Default.getInstance();
	    ArrayList<Course> courses = courseLoader.loadByUserId(userId); 
	    
	    for (int k=0 ; k < courses.size() ; k++ ) { 
	    	Course c = courses.get(k);
			//CourseMembership courseMembership = new CourseMembership();
			//courseMembership.setUserId(userId);
			//courseMembership.setCourseId(c.getId());
	    	CourseMembership courseMembership = courseMembershipLoader.loadByCourseAndUserId(c.getId(),userId);
			
			CourseMembership.Role role = courseMembership.getRole();
			String ident = role.getIdentifier();
			
	    	retval += "Enrolled in: " + c.getTitle() + "(" + c.getCourseId() + ") Role: " + getCourseRoleString(role.getIdentifier()) + "<br/>";
	    //	getCourseRole(c.getCourseId(),userId);
	      }
		}
		catch (Exception e) { } 

		return retval;
	}

	/** 
	 * Return a list of courses associated with this user. 
	 * @param userId BB User Object
	 * @return List of BB Course Objects
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

	
	/**
	 * Get a BB User Object from a username
	 * @param username The BB Username
	 * @return a BB User Object
	 */
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

		
	public String getCourseRoles() { 
		String retval = "";
		CourseMembership.Role[] crsrole = CourseMembership.Role.getAllCourseRoles();
		for (CourseMembership.Role cr : crsrole) 
			retval += cr.toString() + "<br/>-->Name=" + cr.getFieldName()  + " Identifier=" + cr.getIdentifier() +  "<br/>" ;
		return retval;
	}

	
	public String getPortalRoles(Id userId) { 
		String retval = "";
		try {
			PortalRoleDbLoader portalLoader = PortalRoleDbLoader.Default.getInstance();
			PortalRole prole = portalLoader.loadPrimaryRoleByUserId(userId);
			if (prole != null)
				retval = "Primary Role: " + prole.getRoleName() + " " + prole.getRoleID();
			ArrayList<PortalRole> sroles = portalLoader.loadSecondaryRolesByUserId(userId);
			
			for (PortalRole pr : sroles) { 
				retval += "<br/>Secondary Role: " + pr.getRoleName() + " " + pr.getRoleID();
			}									
		}
		
		catch (Exception e) { 
			e.printStackTrace();
		}		
		
		return retval;		
	}
	
	
	/**
	 * User Friendly String for the type of Role passed in. 
	 * @param type  Type of Role: COURSE or SYSTEM
	 * @param role  Internal role constant. ie CourseMembership.Role.GRADER
	 * @return Role as friendly string.
	 */
	static public String getCourseRoleString(String type) {
		String uRole = "*unknown*";
			// get role based on coursemembershipRole (CourseMembership.Role)
			if(type.equals("B")) uRole="Course Builder";
			else if(type.equals("S"))	uRole="Student(Default)";
			else if(type.equals("G"))	uRole="Grader";
			else if(type.equals("U"))	uRole="Guest";
			else if(type.equals("P"))	uRole="Instructor";
			else if(type.equals("S")) 	uRole="Student";
			else if(type.equals("T"))	uRole="Teaching Assistant";
			else if(type.equals("UBC_Secondary_Instructor")) 	uRole = "Secondary Instructor";
			else if(type.equals("UBC_Auditor")) 	uRole = "Auditor";
			return uRole;
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
