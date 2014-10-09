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
	String page_title = "BBCourseList: File List";    
	%>
	
	<bbNG:learningSystemPage ctxId="ctx" title="<%= page_title %>">
	
	<%
	//Get a User instance via the page context
	User sessionUser = ctx.getUser();
    //Get the User's Name and Id
    
 	%>
    <bbNG:pageHeader>	
    	<bbNG:pageTitleBar iconUrl="<%=iconUrl%>"> <%= page_title %></bbNG:pageTitleBar>
    </bbNG:pageHeader>
    
    <br/><b>File Listing for year1</b> <br/>
    <%= util.showDirectoryContents("/institution/UBC_Vancouver/Medicine/mdup/year1") %>
    <hr/>
    <%= util.showDirectoryContents("/institution/UBC_Vancouver/Medicine/common/portal") %>
    
    <br/><br/>
  <a href="courselist.jsp">Return to CourseList</a> | 
   
 
    
</bbNG:learningSystemPage>
