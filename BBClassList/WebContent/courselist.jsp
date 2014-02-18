<%
/*
  file:		helloworld.jsp
  project:	HelloWorld_3b
  description: 
  	Building Blocks Getting Started Guide: Identifying and Listing Course Memberships
  author:	Mark O'Neil
  date:		2006-01-25
  version:	1.0.0
  version history:
  	date : version : note
  	2006-01-23 : 1.0.0 : created(mo)
  	2010-08-12 : 2.0.0 : updated for NG (smh)
*/
%><%@ page	language="java" 
		import="java.util.*,
		        blackboard.base.*,
				blackboard.data.*,
				blackboard.data.user.*,
				blackboard.data.course.*,
				blackboard.persist.*,
				blackboard.persist.user.*,
				blackboard.persist.course.*,
				blackboard.platform.*,
				blackboard.platform.persistence.*"          
		pageEncoding="UTF-8"
%>
	<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%
	String iconUrl = "/images/ci/icons/bookopen_u.gif"; 
	String page_title = "HelloWorld_3b: Identifying and Listing Course Memberships";
	String msg = null;
	String EnrDateStr="";
	

    
	%>
	<bbNG:learningSystemPage ctxId="ctx" title="<%= page_title %>">
	<%
	//Get a User instance via the page context
	User sessionUser = ctx.getUser();
    //Get the User's Name and Id
    String sessionUserName = sessionUser.getUserName();
    Id sessionUserId = sessionUser.getId();
    String sessionUserGivenName = sessionUser.getGivenName();
    String sessionUserFamilyName = sessionUser.getFamilyName();
    String sessionUserEmailAddress = sessionUser.getEmailAddress();
    String sessionUserBatchID = sessionUser.getBatchUid();
    User.SystemRole sessionUserSYSTEMRole = sessionUser.getSystemRole();
    String sessionUserSystemRoleString = sessionUserSYSTEMRole.toString();

    /*
	//Retrieve the Db persistence manager from the persistence service
	BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
	// Retrieve the course identifier from the URL
	String courseIdParameter = request.getParameter("course_id");
	// Generate a persistence framework course Id to be used for loading the course
	// Ids are persistence framework object identifiers.
	Id courseId = bbPm.generateId(Course.DATA_TYPE, courseIdParameter);
	CourseDbLoader courseLoader = (CourseDbLoader)bbPm.getLoader(CourseDbLoader.TYPE);
	Course sessionCourse = courseLoader.loadById(courseId);
    String sessionCourseId = sessionCourse.getCourseId();
    String sessionCourseBatchUID = sessionCourse.getBatchUid(); 
    String sessionCourseCourseTitle = sessionCourse.getTitle(); 
    String sessionCourseDescription = sessionCourse.getDescription();
    String sessionCourseBatchUid = sessionCourse.getBatchUid();
    long sessionCourseUploadLimit = sessionCourse.getUploadLimit();
    boolean sessionCourseIsAvailable = sessionCourse.getIsAvailable();
    boolean sessionCourseAllowGuests = sessionCourse.getAllowGuests(); 
 
    
    // get the membership data to determine the User's Role
    CourseMembership sessionUserCourseMembership = null;
    CourseMembershipDbLoader sessionCourseMembershipLoader = null;
    sessionCourseMembershipLoader = 
        (CourseMembershipDbLoader) bbPm.getLoader(CourseMembershipDbLoader.TYPE);
	try {  
		sessionUserCourseMembership = sessionCourseMembershipLoader.loadByCourseAndUserId(courseId, sessionUserId);
	} catch (KeyNotFoundException e) {
		// There is no membership record.
		msg = "There is no membership record. Better check this out:" + e;
	} catch (PersistenceException pe) {
		// There is no membership record.
		msg  = "An error occured while loading the User. Better check this out:" + pe;
	}
	CourseMembership.Role sessionUserCourseMembershipRole = sessionUserCourseMembership.getRole();
	String sessionUserCourseMembershipRoleStr = sessionUserCourseMembershipRole.toString();
	java.util.Calendar sessionUserCourseMembershipEnrollmentDate = sessionUserCourseMembership.getEnrollmentDate();
	boolean sessionUserCourseMembershipIsAvailable = sessionUserCourseMembership.getIsAvailable();
	  */   
 	%>
    <!-- Start Breadcrumb Navigation --> 
        <bbNG:breadcrumbBar environment="CTRL_PANEL"  navItem="control_panel">
            <bbNG:breadcrumb>Hello World</bbNG:breadcrumb>
        </bbNG:breadcrumbBar>
    <!-- End Breadcrumb Navigation -->
    <bbNG:pageHeader>	
    	<bbNG:pageTitleBar iconUrl="<%=iconUrl%>">
        <%= page_title %>
    	</bbNG:pageTitleBar>
    </bbNG:pageHeader>
    List Session User and Session Course Information. <br>
    Hi <%= sessionUserGivenName %> <%= sessionUserFamilyName %>, <br>
    Your User Name is: <%= sessionUserName %> <br>
    Your email address is: <%= sessionUserEmailAddress %> <br>
    Your batch_uid is: <%= sessionUserBatchID %> <br>
    Your system role is: <%= sessionUserSystemRoleString %> <br>
    <!-- return User Friendly Role -->
	Your UI friendly System Role is: "<%= getRoleString( "SYSTEM", sessionUserSYSTEMRole ) %>"<br>
    
    
</bbNG:learningSystemPage>

<%!
static public String getRoleString(String type, Object role) {
	// return a User Friendly String for the type Role passed in
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

%>