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
	String page_title = "HelloWorld_3b-b: Listing Course Memberships";
	String msg = null;
	String EnrDateStr="";
	
	Comparator cmSortByUsername = new Comparator() {
      public int compare(Object o1, Object o2) {
        CourseMembership cm1 = (CourseMembership) o1;
        CourseMembership cm2 = (CourseMembership) o2;
        String s1 = (String)((User)cm1.getUser()).getUserName();
        String s2 = (String)((User)cm2.getUser()).getUserName();
        return s1.toLowerCase().compareTo(s2.toLowerCase());
      }
    };
    
    Comparator cmSortByFamilyName = new Comparator() {
      public int compare(Object o1, Object o2) {
        CourseMembership cm1 = (CourseMembership) o1;
        CourseMembership cm2 = (CourseMembership) o2;
        String s1 = (String)((User)cm1.getUser()).getFamilyName();
        String s2 = (String)((User)cm2.getUser()).getFamilyName();
        int compare = s1.toLowerCase().compareTo(s2.toLowerCase());
        if( compare == 0 ) {
            s1 = (String)((User)cm1.getUser()).getGivenName();
            s2 = (String)((User)cm2.getUser()).getGivenName();
            compare = s1.toLowerCase().compareTo(s2.toLowerCase());
        }
        return compare;
      }
    };
   
    Comparator cmSortByRole = new Comparator() {
      public int compare(Object o1, Object o2) {
        CourseMembership cm1 = (CourseMembership) o1;
        CourseMembership cm2 = (CourseMembership) o2;
        String s1 = getRoleString( "COURSE", (CourseMembership.Role)cm1.getRole() );
        String s2 = getRoleString( "COURSE", (CourseMembership.Role)cm2.getRole() );
        int compare = s1.toLowerCase().compareTo(s2.toLowerCase());
        if ( compare == 0 ) {
        		s1 = (String)((User)cm1.getUser()).getFamilyName();
        		s2 = (String)((User)cm2.getUser()).getFamilyName();
        		compare = s1.toLowerCase().compareTo(s2.toLowerCase());
        		if( compare == 0 ) {
         	   	s1 = (String)((User)cm1.getUser()).getGivenName();
            		s2 = (String)((User)cm2.getUser()).getGivenName();
            		compare = s1.toLowerCase().compareTo(s2.toLowerCase());
        		}
        	}
        return compare;
      }
    };
    
    Comparator cmSortByEmailAddress = new Comparator() {
      public int compare(Object o1, Object o2) {
        CourseMembership cm1 = (CourseMembership) o1;
        CourseMembership cm2 = (CourseMembership) o2;
        String s1 = (String)((User)cm1.getUser()).getEmailAddress();
        String s2 = (String)((User)cm1.getUser()).getEmailAddress();
        return s1.toLowerCase().compareTo(s2.toLowerCase());
      }
    };
    
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
    <!-- 
    List Session User and Session Course Information. <br>
    Hi <%= sessionUserGivenName %> <%= sessionUserFamilyName %>, <br>
    Your User Name is: <%= sessionUserName %> <br>
    Your email address is: <%= sessionUserEmailAddress %> <br>
    Your batch_uid is: <%= sessionUserBatchID %> <br>
    Your system role is: <%= sessionUserSystemRoleString %> <br>
     -->
    <!-- return User Friendly Role 
	Your UI friendly System Role is: "<%= getRoleString( "SYSTEM", sessionUserSYSTEMRole ) %>"<br>
    -->
    <p>Information about this course. <br>
    <%= sessionCourseCourseTitle %> [BatchUID=<%= sessionCourseBatchUID %> : CourseId=<%= sessionCourseBatchUID %>] <br>
    <ul> <%= sessionCourseDescription%> </ul><br>
    Is this course available? <%= sessionCourseIsAvailable?"Yes":"No" %> <br>
    Guests are allowed? <%= sessionCourseAllowGuests?"Yes":"No" %> <br>
    The current upload limit is: <%= sessionCourseUploadLimit %> <p>
	<% 
	//Get date information
	int enrYear = sessionUserCourseMembershipEnrollmentDate.get(Calendar.YEAR);
	int enrMonth = sessionUserCourseMembershipEnrollmentDate.get(Calendar.MONTH);
	int enrDay = sessionUserCourseMembershipEnrollmentDate.get(Calendar.DATE);
	EnrDateStr = "" + enrMonth  + "/" +  enrDay + "/" +  enrYear;
	%>
	<p>Information about this membership. <br>
	The availability of this membership is: 
	    <%= sessionUserCourseMembershipIsAvailable?"Available":"Unavailable"%> <br>
	Date membership created: <%= EnrDateStr %> <br>
    The User's Role is: <%= sessionUserCourseMembershipRoleStr %> </p>
	<!-- return User Friendly Role -->
	Your UI friendly Course Membership Role is: "<%= getRoleString("COURSE", sessionUserCourseMembershipRole ) %>"
	<p>
	<%
	List allMembershipsList = sessionCourseMembershipLoader.loadByCourseId( courseId, null, true );
	Collections.sort(allMembershipsList, cmSortByFamilyName);	
	%>
    <bbNG:inventoryList 
    	collection="<%= allMembershipsList %>" 
		objectVar="cm" 
		className="CourseMembership"
		emptyMsg="No data to display">
		<bbNG:listElement 
			label="Name" 
			name="Name" 
			isRowHeader="true"
			comparator="<%= cmSortByFamilyName %>">
    	    	<%= cm.getUser().getGivenName() %> <%= cm.getUser().getFamilyName() %>
    	</bbNG:listElement>
		<bbNG:listElement 
			label="Username" 
			name="Username" 
			comparator="<%= cmSortByUsername %>">
			<%= cm.getUser().getUserName() %>
		</bbNG:listElement>
		<bbNG:listElement 
			label="Email" 
			name="Email" 
			comparator="<%= cmSortByEmailAddress %>">
			<%= cm.getUser().getEmailAddress() %>
		</bbNG:listElement>
		<bbNG:listElement 
			label="Role" 
			name="Role" 
			comparator="<%= cmSortByRole %>">
			<%= getRoleString( "COURSE", cm.getRole() ) %>
		</bbNG:listElement>
	</bbNG:inventoryList>
	</p>
    <div align="right"> 
        <form>
            <bbNG:button id="back" label="Back" onClick="javascript:history.go(-1);"/>
       </form>
    </div>
    <a href="helloworld.jsp">Return to Hello World with User</a>
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