<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ page language="java" import="blackboard.persist.course.*,blackboard.data.course.*,blackboard.persist.*,blackboard.platform.persistence.*" %>
<%@ page import="com.spvsoftwareproducts.blackboard.utils.B2Context"  errorPage="../error.jsp"%>

<bbNG:includedPage>
<h2>Hello BB World</h2>
    <%   
    B2Context b2Context = new B2Context(request);
    String tabId = b2Context.getRequestParameter("tab_tab_group_id", "");
    String launchUrl1 = b2Context.getPath() + "module/view-bbtags.jsp?tab=" + tabId; 
    pageContext.setAttribute("launchUrl1", launchUrl1);
    String launchUrl2 = b2Context.getPath() + "module/view-nocourse.jsp?tab=" + tabId; 
    pageContext.setAttribute("launchUrl2", launchUrl2);

%>

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
 BatchUID=<%=course.getBatchUid()%> %>
 
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
<% 
out.println("<br/>Id value of courseId:" + courseId + "<br/> as toString:" + courseId.toString());
%>

  <%}%>
<p><a href="${launchUrl1}">With BB Taglibs</a> | <a href="${launchUrl2}">View No Course</a> </p>

</bbNG:includedPage>