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

<p><a href="view-bbtags.jsp">With BB Taglibs</a> | <a href="view-nocourse.jsp">View No Course</a> </p>

</bbNG:includedPage>