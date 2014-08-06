<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page	language="java" 
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

<%@ page import="com.spvsoftwareproducts.blackboard.utils.B2Context"  errorPage="../error.jsp"%>

<%
	String iconUrl = "/images/ci/icons/bookopen_u.gif"; 
	String page_title = "UBC FoM B2 Template";   
%>

<bbNG:learningSystemPage ctxId="ctx" title="<%= page_title %>">
    <!-- Start Breadcrumb Navigation --> 
    <!-- End Breadcrumb Navigation -->
    <bbNG:pageHeader>	
    	<bbNG:pageTitleBar iconUrl="<%=iconUrl%>">
        <%= page_title %>
    	</bbNG:pageTitleBar>
    </bbNG:pageHeader>

    <%           
    // Construct the URL that the form will use to call the Servlet for processing. Must add specific BB info. 
    //   The destination URL is defined in web.xml, just like a regular web servlet process.
    // set it as a page attribute to be available later in this page.
    B2Context b2Context = new B2Context(request);     // This is the Stephen Vickars library that adds value. 
    String launchUrl = b2Context.getPath() + "GetInfo";
    pageContext.setAttribute("launchUrl", launchUrl);

    // Get the logged in username  
    // set it as a page attribute to be available later in this page.    		

    User sessionUser = ctx.getUser();
    //Get the User's Name and Id
    String sessionUserName = sessionUser.getUserName();
    pageContext.setAttribute("myusername",sessionUserName);
    
%>
	
You are logged in as: <%= sessionUserName %><br/>
Click submit below for more information. 


    <bbNG:form action="${launchUrl}" method="POST" id="id_userform" name="UserForm" >

     <bbNG:dataCollection showSubmitButtons="true" >
                      
     <bbNG:step title="Click for more information">


      <bbNG:dataElement  label="Username">
        <bbNG:textElement name="username" size="50" value="${myusername}" displayOnly="true"/>
      </bbNG:dataElement>     
     
    </bbNG:step>
    
          
   <bbNG:stepSubmit>
    <bbNG:stepSubmitButton label="Submit" />
   </bbNG:stepSubmit>
   
  </bbNG:dataCollection>
</bbNG:form>


</bbNG:learningSystemPage>