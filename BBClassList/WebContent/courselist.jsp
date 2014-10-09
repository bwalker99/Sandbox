<%
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
<%@page import="com.spvsoftwareproducts.blackboard.utils.B2Context" %>

	<%@ taglib uri="/bbNG" prefix="bbNG" %>
<%
	String iconUrl = "/images/ci/icons/bookopen_u.gif"; 
	String page_title = "BBCourseList: Identifying and Listing Courses";    
	%>
	
	<bbNG:includedPage ctxId="ctx">
	
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
    <bbNG:pageHeader>	
    	<bbNG:pageTitleBar iconUrl="<%=iconUrl%>"> <%= page_title %></bbNG:pageTitleBar>
    </bbNG:pageHeader>
    
    List Session User and Session Course Information. v1.0.2<br>
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
       User tempuser = util.getUser("medinst1");    		
    %>
    <br/><b>From embedded java file - current user:</b><br/>
    <%= util.getCoursesByUserString(sessionUserId) %>
    <br/><b>Different user: <i>medinst1:</i></b> <br/>
    <%= util.getCoursesByUserString(tempuser.getId()) %>
     
    <br/><b>All Course Roles</b> <br/>
     <%= util.getCourseRoles() %>

    <br/><b>Institution Roles</b> <br/>
    <%= util.getPortalRoles(sessionUserId) %>
     -->
    <br/><b>File Listing for year1</b> <br/>
    <pre>
    <%= util.showDirectoryContents("/institution/UBC_Vancouver/Medicine/mdup/year1",1) %>
    </pre>
    <hr/>
    <pre>
    <%= util.showDirectoryContents("/institution/UBC_Vancouver/Medicine/common/portal",1) %>
    </pre>
    
    <br/><br/>
  Other pages: <a href="classlist.jsp">Original Classlist page</a> 
    | <a href="helloworld.jsp">Original HelloWorld page</a>
    | <a href="filelist.jsp">FileList</a>
  
    
    
</bbNG:includedPage>
