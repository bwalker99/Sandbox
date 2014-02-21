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
	String page_title = "BBCourseList: Identifying and Listing Courses";
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

	Your UI friendly System Role is: "<%= ca.ubc.med.blackboard.Util.getRoleString( "SYSTEM", sessionUserSYSTEMRole ) %>"<br>
    
    <!--  Get courses for user and list -->
    <%
       ca.ubc.med.blackboard.Util util = new ca.ubc.med.blackboard.Util();
       User tempuser = util.getUser("bwalk99");    		
    %>
    <br/><b>From embedded java file - current user:</b><br/>
    <%= util.getCoursesByUserString(sessionUserId) %>
    <br/><b>Different user: <i>bwalk99:</i></b> <br/>
    <%= util.getCoursesByUserString(tempuser.getId()) %>
    
    
</bbNG:learningSystemPage>
