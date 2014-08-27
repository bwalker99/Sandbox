<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page	language="java" 
		import="java.util.ArrayList,
				blackboard.data.user.User,
				ca.ubc.med.blackboard.b2template.data.MyBbCourse"          
		pageEncoding="UTF-8"
%>

<%@ page import="com.spvsoftwareproducts.blackboard.utils.B2Context"  errorPage="../error.jsp"%>

<jsp:useBean id="user" class="blackboard.data.user.User" scope="request" />
<jsp:useBean id="mycourses" class="java.util.ArrayList" scope="request" /> <!-- an ArrayList of MyBbCourse objects -->
<jsp:useBean id="userdata" class="java.lang.String" scope="request" />
<jsp:useBean id="message" class="java.lang.String" scope="request" />
<jsp:useBean id="lastlogin" class="java.lang.String" scope="request" />

<%
	String iconUrl = "/images/ci/icons/bookopen_u.gif"; 
	String page_title = "UBC FoM B2 Template - Result";   
%>

<bbNG:learningSystemPage ctxId="ctx" title="<%= page_title %>">
    <bbNG:pageHeader>	
    	<bbNG:pageTitleBar iconUrl="<%=iconUrl%>">
        <%= page_title %>
    	</bbNG:pageTitleBar>
    </bbNG:pageHeader>

  <%
    // Construct the return url
    B2Context b2Context = new B2Context(request);     // This is the Stephen Vickers library that adds value. 
    String returnUrl = b2Context.getPath() + "start.jsp";
    pageContext.setAttribute("returnUrl", returnUrl);
        
   %>
	
${message}<br/>	
Logged in information using BB User Object:<br/>
<table>
<tr><td align="right">UserName:</td><td>${user.userName}</td></tr>
<tr><td align="right">Name:</td><td>${user.givenName} ${user.familyName}</td></tr>
<tr><td align="right">Batch UID:</td><td>${user.batchUid}</td></tr>
<tr><td align="right">Email:</td><td>${user.emailAddress}</td></tr>
<tr><td align="right">Available flag:</td><td>${user.isAvailable}</td></tr>
<!-- <tr><td align="right">Last Login Date:</td><td>${user.lastLoginDate}</td></tr>  Ugly. returns full Calendar string -->
<tr><td align="right">Last Login Date:</td><td>${lastlogin}</td></tr> <!--  as String -->
</table>
<br/>
<% 
System.out.println("Displayed user info. About to display course");
%>
Enrolled Courses:<br/>

<bbNG:inventoryList 
    	collection="${mycourses}" 
		objectVar="clist" 
		className="ca.ubc.med.blackboard.b2template.data.MyBbCourse" 
		emptyMsg="No data to display">
		
		<bbNG:listElement label="CourseID" 	name="CourseID" >
    	    	${clist.courseId}
    	</bbNG:listElement>
    	
    	<bbNG:listElement 	label="Course Role" 	name="CourseRole"> 		
				${clist.role}
		</bbNG:listElement>
		
		<bbNG:listElement 	label="Role Identifier" 	name="RoleIdentifier"> 		
			${clist.roleIdentifier}			
		</bbNG:listElement>
    	
    	
	</bbNG:inventoryList>



<table>
 <tr><th>CourseId</th><th>CourseName</th><th>Description</th><th>Role</th><th>Role Ident</th></tr>
  <c:forEach var="clist" items="${mycourses}" >
  <tr>
      <td>${clist.courseId}</td><td>${clist.courseName}</td><td>${clist.description}</td>
      <td>${clist.role}</td><td>${clist.roleIdentifier}</td>
  </tr>
 </c:forEach>       
</table>
<br/>

Userdata supplied information: ${userdata}<br/> <br/>
<a href="${returnUrl}">Return to start page </a><br/><br/>

<!-- Display the B2 version -->
<small><i>Version: <%=ca.ubc.med.blackboard.b2template.Util.VERSION %></i></small>

</bbNG:learningSystemPage>