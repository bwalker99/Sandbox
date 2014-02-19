<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ page language="java" import="blackboard.persist.course.*,blackboard.data.course.*,blackboard.persist.*,blackboard.platform.persistence.*" %>
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
Email Address: <%=bbContext.getUser().getEmailAddress() %><br/><br/>
Request URL: <%=bbContext.getRequestUrl()%><br/> 

<%}%>

<%
// if inside a course
if(bbContext.hasCourseContext()){ 
	Course course = bbContext.getCourse();
	String courseIdParam = request.getParameter("course_id");
%>


<hr/>You are in the course:<br/> 
 CourseID=<%=course.getCourseId() %><br/>
 Title=<%=course.getTitle() %><br/>
 Description=<%=course.getDescription() %>
 
<%
  out.println("<br/>CourseID as Param=" + courseIdParam);
// Get course object from parameter:
BbPersistenceManager bbPm = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
Id courseId = bbPm.generateId(Course.DATA_TYPE, courseIdParam);
CourseDbLoader courseLoader = (CourseDbLoader)bbPm.getLoader(CourseDbLoader.TYPE);
Course paramCourse = courseLoader.loadById(courseId);
%>

<br/><br/>Course from Parameter:<br/>
CourseID=<%=paramCourse.getCourseId() %><br/>
Title=<%=paramCourse.getTitle() %><br/>
Description=<%=paramCourse.getDescription() %>

  <%}%>
<p><a href="<%=bbContext.getRequestUrl()%>/view-bbtags.jsp">With BB Taglibs</a> | <a href="view-nocourse.jsp">View No Course</a> </p>

</bbNG:includedPage>