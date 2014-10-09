package ca.ubc.med.blackboard;

import java.util.ArrayList;
import java.util.List;

import blackboard.data.user.*;
import blackboard.data.course.*;
import blackboard.data.content.*;

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

import blackboard.cms.filesystem.CSEntry;
import blackboard.cms.filesystem.CSDirectory;
import blackboard.cms.filesystem.CSContext;


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
	    	System.out.println(retval);
	    	retval += showMapView2(c.getId());	    
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
	
public String showDirectoryContents(String dirname,int level) { 
	String retval = space("",level * 2,false) + dirname + "\n";
	java.util.List<CSEntry> dirContents = getDirectoryContents(dirname);
	
	for (CSEntry entry : dirContents) { 

		com.xythos.storageServer.api.FileSystemEntry x_entry = entry.getFileSystemEntry();
		if (entry instanceof CSDirectory) 
			retval += showDirectoryContents(entry.getFullPath(),level + 1); // Note: recursive call 
		else 
			retval += space("",level * 4,false) + entry.getBaseName() + " " + entry.getCreationTimestamp() + " " + 
					entry.getLastUpdateTimestamp() + " " + entry.getSize() + "\n"; // "<br/>";		
	}
	
	return retval;
}
public java.util.List<CSEntry> getDirectoryContents(String dirname) { 
	java.util.List<CSEntry> dirContents = new java.util.ArrayList<CSEntry>();
	
	 CSContext ctxCS=null;
	 try {
	      ctxCS = CSContext.getContext();
	      
	      CSDirectory dir = null;
	      CSEntry entry = ctxCS.findEntry(dirname);
	      if (entry instanceof CSDirectory) { 
	    	  dir = (CSDirectory)entry;
	      }
	 
	      if (dir != null) { 
	    	  dirContents = dir.getDirectoryContents();
	      }
	 
	 } catch (Exception e) {
	      ctxCS.rollback();
	 } finally {
	      if (ctxCS!=null) {     
	    	  try {  ctxCS.commit();} catch(Exception e2) {}       
	    	  } 
	      }
	 
	 return dirContents;
}

/*
 * SHows all the content in aourse.  
 * All content is returned into the list, even though you don't know where it sits in 
 * the heirarchy. That is, all items, or files, and folders are in the same list.
 */ 
public String showMapView(Id courseId) {
    String retval = "<i>Course Content:<br/>";
	java.util.List<Content> content = null;
	
	try { 
		blackboard.persist.content.ContentDbLoader contentLoader = blackboard.persist.content.ContentDbLoader.Default.getInstance();
		content = contentLoader.loadMapView(courseId,null);
		for (Content con : content) {
			retval += " -->" + con.getTitle() + " Avail=" + con.getIsAvailable() + " Folder=" + con.getIsFolder() + "<br/>";
			blackboard.base.BbList<Content> list = contentLoader.loadListById(con.getId());
			for (Content con2 : list) {				
				String temp = "";
				 blackboard.base.BbList<ContentFile> confile = con2.getContentFiles();
				for (ContentFile cf : confile) { 
					temp += cf.getName() + " " + cf.getLinkName();
				}				
				retval += " -->-->" + con2.getTitle() + " Avail=" + con2.getIsAvailable() + " Folder=" + con2.getIsFolder() 
						+ " DataType=" + con2.getDataType().getName() + " Files=" + temp +  "<br/>";
			}			
		}		 
	}
	catch (PersistenceException pe) { 
		System.err.println("Error" + pe.getMessage());		
	}
	
	return retval + "<br/>";
}

/** 
 * Uses recursive call to go down into content folder. 
 * Problem is that all content below a level is returned into the list, even though you don't know where it sits in 
 * the heirarchy. 
 * 
 * @param courseId
 * @return
 */
public String showMapView2(Id courseId) {
    String retval = "<i>Course Content:<br/>";
	java.util.List<Content> content = null;
	
	try { 
		blackboard.persist.content.ContentDbLoader contentLoader = blackboard.persist.content.ContentDbLoader.Default.getInstance();
		content = contentLoader.loadMapView(courseId,null);
		for (Content con : content) {  // First time through
			retval += " -->" + con.getTitle() + " Avail=" + con.getIsAvailable() + " Folder=" + con.getIsFolder() + "<br/>";
			if (con.getIsFolder()) {
				blackboard.base.BbList<Content> list = contentLoader.loadListById(con.getId());
				retval += showList(list,2);
			}			
		}		 
	}
	catch (PersistenceException pe) { 
		System.err.println("Error" + pe.getMessage());		
	}
	
	return retval + "<br/>";
}

/**
 * Show one list. Recurses if it finds a folder. 
 * @param content
 * @param level
 * @return
 */
private String showList(java.util.List<Content> content,int level) {
	System.out.println("Calling showList with level=" + level);
	boolean first = true;
	
	String retval = "";
for (Content con : content) {				
	String temp = "";
	blackboard.base.BbList<ContentFile> confile = con.getContentFiles();
	for (ContentFile cf : confile) { temp += cf.getName() + " " + cf.getLinkName();	}
	
	retval += showLevel(level) + con.getTitle() + " Avail=" + con.getIsAvailable() + " Folder=" + con.getIsFolder() 
			+ " DataType=" + con.getDataType().getName() + " Path=" + con.getOfflinePath() 
			+ " URL=" + con.getUrl() + " Files=" + temp +  "<br/>";
	if (con.getIsFolder() && !first) {   // Don't recurse for the first, which is always the parent folder repeated.
		System.out.println("Found a folder: " + con.getTitle());
		try { 
			blackboard.persist.content.ContentDbLoader contentLoader = blackboard.persist.content.ContentDbLoader.Default.getInstance();
			blackboard.base.BbList<Content> list = contentLoader.loadListById(con.getId());
		    retval += showList(list,level + 1);   // Note: recursive call
		}
		catch (PersistenceException pe) { 
			System.err.println("Error" + pe.getMessage());		
		}		
	}
	first = false;
   }
return retval;
}

private String showLevel(int level) { 
	String retval = " ";
	for (int k = 0 ; k < level ; k++)
		retval += "-->";
	return retval;
}
/**
 * Space out a string to a specific length
 * @param spacee - the original string
 * @param l  - the new length required - either appended or prepended with spaces.
 * @param front - true if prepend spaces, false if append.
 */
private String space(String spacee, int l, boolean front) {
//String temp_spacee = spacee.trim();   // Why were we trimming it first???
String temp_spacee = spacee;
if (temp_spacee.length() > l) return temp_spacee.substring(0, l-1);
StringBuffer SB = new StringBuffer(" ");
SB.setLength(l - temp_spacee.length());
String spaces = SB.toString().replace('\0', ' ');
if (front) return spaces + temp_spacee;
return temp_spacee + spaces;
/*  StringBuffer SB = new StringBuffer(spacee);
SB.setLength(l);
return SB.toString().replace('\0', ' ');
*/
}

}
