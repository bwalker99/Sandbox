<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ page language="java" import="blackboard.persist.course.*,blackboard.data.course.*" %>
<bbNG:includedPage>
<h2>Hello BB World</h2>

<% 
if (bbContext.getUser().getUserName().equalsIgnoreCase("guest")){ 
%>
You are not logged in.
<%
}else{ 
%>
You are logged in as: <%=bbContext.getUser().getUserName() %>.<br/>
First name: <%=bbContext.getUser().getGivenName() %><br/>
Last name: <%=bbContext.getUser().getFamilyName() %><br/>
Student Number: <%=bbContext.getUser().getStudentId() %><br/>
Email Address: <%=bbContext.getUser().getEmailAddress() %><br/> 

<%}%>

<%
// if inside a course
if(bbContext.hasCourseContext()){ 
	Course course = bbContext.getCourse();
%>

<hr/>You are in course <%=course.getCourseId() %>:<%=course.getTitle() %>:<%=course.getDescription() %>.
<%}%>

</bbNG:includedPage>