<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ page language="java" import="blackboard.persist.course.*,blackboard.data.course.*" %>

<bbNG:genericPage title="This is the &lt;bbNG:genericPage&gt; title property" entitlement="system.admin.VIEW">
<bbNG:pageHeader instructions="This is the &lt;bbNG:pageHeader&gt; instructions property">
		<bbNG:breadcrumbBar environment="SYS_ADMIN_PANEL" navItem="admin_plugin_manage">
      		<bbNG:breadcrumb href="view.jsp" title="${bundle['plugin.name']}" />
      		<bbNG:breadcrumb title="&lt;bbNG:breadcrumb/&gt; title property" />
    	</bbNG:breadcrumbBar>	    
	    <bbNG:pageTitleBar iconUrl="${bundle['plugin.logo']}" showIcon="true" showTitleBar="true" title="This is the &lt;bbNG:pageTitleBar&gt; title property"/>
</bbNG:pageHeader>

<h2>Hello BB World with Tags</h2>

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


<hr/>You are in the course:<br/>
<ul> 
 <li>CourseID=<%=course.getCourseId() %></li>
 <li>Title=<%=course.getTitle() %></li>
 <li>Description=<%=course.getDescription() %></li>
 </ul>
<%}%>

<p><a href="view.jsp">Without BB Taglibs</a> | <a href="view-nocourse.jsp">View No Course</a> </p>
</bbNG:genericPage>
