package ca.ubc.med.blackboard;

import java.util.ArrayList;

import blackboard.data.course.Course;
import blackboard.data.user.*;
import blackboard.persist.Id;
import ca.ubc.med.idm.ExternalInterface;
import ca.ubc.med.idm.data.mdupUser;

public class LoadMdupPerson {

	public MdupPerson load(String username) { 
		MdupPerson MP = new MdupPerson();
		
		ExternalInterface fomInfo = new ExternalInterface();
		
		Util util = new Util();
		User user = util.getUser(username);  // Blackboard user object	
		if (user == null) { 
			System.out.println("Error getting user object for: " + username);
		}

		String[] mdupCourses = fomInfo.getCourseInfo();
		
		for (int k=0 ; k < mdupCourses.length ; k++) { 
			String[] temp = mdupCourses[k].split(",");
			MP.setMdupCourseInfo(Integer.parseInt(temp[0]),temp[1],temp[2]);
		}			
		
		ArrayList<Course> courses = util.getCoursesByUser(user.getId());

	    for (int k=0 ; k < courses.size() ; k++ ) { 
	    	Course c = courses.get(k);
	    	
	    	System.out.println(c.getCourseId() + c.getTitle() +util.getUserCourseRole(c.getCourseId(),user.getId()) +
	    			util.getUserCourseGroup(c.getCourseId(),user.getId()));
	    	
	    	MP.setBlackboardInfo(c.getCourseId(),c.getTitle(),util.getUserCourseRole(c.getCourseId(),user.getId()),
	    			util.getUserCourseGroup(c.getCourseId(),user.getId()));	    	
	      }
	    
		MP.setMdupPersonInfo(fomInfo.getMdupUser(username,null));
		
		
		return MP;
	}
}
