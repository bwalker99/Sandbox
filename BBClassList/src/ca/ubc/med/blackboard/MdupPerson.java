package ca.ubc.med.blackboard;

import java.util.ArrayList;
//import blackboard.data.course.Group;
import blackboard.platform.log.LogService;
import blackboard.platform.log.LogServiceFactory;


/**
 * This class summarizes a users status in Blackboard. It is used to determine whether the user has all the correct role
 * and is a member of the right groups.<br/>
 * It can also be used to display this information to the user. 
 * @author rwalker
 *
 */
public class MdupPerson {
	
	private static final LogService LOG = LogServiceFactory.getInstance();
	//ca.ubc.med.idm.data.mdupUser mdupUser;
	boolean valid;
	
	// An 5 member array of courseStatus classes, representing Year 1-4 and Common. 
	ArrayList<courseStatus> courses;
	
	// For return and display
	public ArrayList getCourses() { 
		return courses;
	}
	
	public MdupPerson() { 		
		courses  = new ArrayList<courseStatus>(5);
		
		for (int k=0 ; k < 5 ; k++) 
			courses.add(new courseStatus()); // Add blank courseStatus records
		
		LOG.logDebug("Init mdupPerson");
		System.out.println("Init mdupPerson courses.size=" + courses.size());
	}
	
	/**
	 * Set information 
	 * @param U
	 */
	public void setMdupPersonInfo(ca.ubc.med.idm.data.mdupUser U) { 
	
		LOG.logDebug("setMdupPersonInfo:" + U.toString());
		
		courseStatus cs = courses.get(0);
		cs.setRoleExpected(U.getYear1().equals("I") ? "Instructor" : "Auditor");
		cs.setGroupExpected(U.getYear1().equals("I") ? "Instructors" : "Auditors");
		
		cs = courses.get(1);
		cs.setRoleExpected(U.getYear1().equals("I") ? "Instructor" : "Auditor");
		cs.setGroupExpected(U.getYear1().equals("I") ? "Instructors" : "Auditors");				
	}
	
	/**
	 * Set Course information from MDUP
	 * @param year The course year (1=1, Common = 5)
	 * @param courseId The Blackboard courseid stored in MDUP
	 * @param courseDesc The MDUP Course descripiont (ie 'Year 1')
	 */
	public void setMdupCourseInfo(int year,String courseId,String courseDesc) {
		
		LOG.logDebug("setMdupCourseInfo: " +  courseId);
		System.out.println("setMdupCourseInfo: " +  year + courseId + courseDesc);
		courseStatus crs = courses.get(year - 1);
		crs.setCourseId(courseId);
		crs.setCourseDesc(courseDesc);
	}
	
	
	/**
	 * Set Information from Blackboard into the courses
	 * @param courseId the Blackboard courseid
	 * @param role The existing Blackboard role for this course
	 * @param group The existing Blackboard group for this course (note: only groups we care about)
	 * @return
	 */
	public boolean setBlackboardInfo(String courseId, String courseName, String role,String group) {
		boolean retval = false;
		
		LOG.logDebug("setBlackboardInfo: " +  courseId);
		System.out.println("setBlackboardInfo: " +  courseId);
		
		for (int k = 0 ; k < 5 ; k++)   {
			courseStatus crs = courses.get(k); 
			if (crs.getCourseId() != null && crs.getCourseId().equals(courseId)) {  // Find the right course.
				crs.setCourseName(courseName);
				crs.setRoleExisting(role);
				crs.setGroupExisting(group);
				retval = true;
				break;
			}			
		}
		return retval;
	}
	
	public boolean checkValid() { 
	boolean retval = false;

	for (int k = 0 ; k < 5 ; k++)   {
		courseStatus crs = courses.get(k); //(courseStatus)courses[k];
		 retval = crs.checkValid();
		 if (retval == false)   // no point going any further. At least one course is not valid, so the whole user profile is not valid	
			break;
		}				
	return retval;
	}
	
	public class courseStatus { 
		String courseId;
		String courseName;
		String courseDesc;
		String roleExisting;
		String roleExpected;
		String groupExisting;
		String groupExpected;
		boolean valid;
		
		public courseStatus() { 
		courseId = null;
		courseName = null;
		courseDesc = null;
		roleExisting = null;
		roleExpected = null;
		groupExisting = null;
		groupExpected = null;
		valid = false;				
		}
		
		public String getCourseId() {
			return courseId;
		}
		public void setCourseId(String courseId) {
			this.courseId = courseId;
		}
		public String getCourseName() {
			return courseName;
		}
		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}
		public String getCourseDesc() {
			return courseDesc;
		}
		public void setCourseDesc(String courseDesc) {
			this.courseDesc = courseDesc;
		}
		public String getRoleExisting() {
			return roleExisting;
		}
		public void setRoleExisting(String roleExisting) {
			this.roleExisting = roleExisting;
		}
		public String getRoleExpected() {
			return roleExpected;
		}
		public void setRoleExpected(String roleExpected) {
			this.roleExpected = roleExpected;
		}
		public String getGroupExisting() {
			return groupExisting;
		}
		public void setGroupExisting(String groupExisting) {
			this.groupExisting = groupExisting;
		}
		public String getGroupExpected() {
			return groupExpected;
		}
		public void setGroupExpected(String groupExpected) {
			this.groupExpected = groupExpected;
		}
		public boolean isValid() {
			return valid;
		}
		public void setValid(boolean valid) {
			this.valid = valid;
		}
		
		public boolean checkValid() {
			valid = (roleExpected.equalsIgnoreCase(roleExisting) && groupExpected.equalsIgnoreCase(groupExisting));
			return valid;
		}

		
	}

}
