<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Import statement for java libraries used in embedded java in the the jsp page.
 We typically don't want to have java in the jsp page, but this is an example in case we do. --> 
<%@ page	language="java" 
		import="java.util.*,
				blackboard.data.*,
				blackboard.data.user.*,
				blackboard.data.course.*"          
		pageEncoding="UTF-8"
%>
<!--  Specific third party library.  -->
<%@ page import="com.spvsoftwareproducts.blackboard.utils.B2Context"  errorPage="../error.jsp"%>

<%
	String iconUrl = "/images/ci/icons/bookopen_u.gif"; 
	String page_title = "UBC FoM B2 Template - Start";   
%>

<bbNG:learningSystemPage ctxId="ctx" title="<%= page_title %>">
    <bbNG:pageHeader>	
    	<bbNG:pageTitleBar iconUrl="<%=iconUrl%>"><%= page_title %></bbNG:pageTitleBar>
    </bbNG:pageHeader>

    <%           
    // Construct the URL that the form will use to call the Servlet for processing. Must add specific BB info. 
    //   The destination URL is defined in web.xml, just like a regular web servlet process.
    // set it as a page attribute to be available later in this page.
    B2Context b2Context = new B2Context(request);     // This is the Stephen Vickers library that adds value. 
    String launchUrl = b2Context.getPath() + "GetInfo";
    pageContext.setAttribute("launchUrl", launchUrl);

    // Get the logged in username      		
    User sessionUser = ctx.getUser();
    String userName = sessionUser.getUserName();
    String givenName = sessionUser.getGivenName();
    String familyName = sessionUser.getFamilyName();
    
    // set it as a page attribute to be available later in this page.
    // Recall that this is embedded java code, so we must provide a way of passing this variable to the jsp
    pageContext.setAttribute("myusername",givenName + " " + familyName + "(" + userName + ")");
    pageContext.setAttribute("username",userName);
    
%>
	
<h2>UBC FoM B2 Template</h2>	
You are logged in as: ${myusername}<br/> <!-- Note. This displays the variable defined above. -->
(Can also display with java variables in the jsp: <%= givenName %>&nbsp;<%= familyName %>)<br/>
Click submit below for more information. 
<!-- The following acts just like a normal web form. Will post to servlet defined in web.xml as 'GetInfo' -->

    <bbNG:form action="${launchUrl}" method="POST" id="id_userform" name="UserForm" >

     <bbNG:dataCollection showSubmitButtons="true" >
                      
     <bbNG:step title="Click for more information">

      <bbNG:dataElement  label="Username">
        <bbNG:textElement name="username" value="${username}" displayOnly="true"/> <!-- Pass value to servlet -->
      </bbNG:dataElement>
      
      <bbNG:dataElement  label="Userdata">
        <bbNG:textElement name="userdata" value="Phrase to process"/> <!--  Editable value to pass to the servlet -->
      </bbNG:dataElement>     
                
    </bbNG:step>
    <!-- You can add more steps here. -->
              
   <bbNG:stepSubmit>
    <bbNG:stepSubmitButton label="Submit" />
   </bbNG:stepSubmit>
   
  </bbNG:dataCollection>
</bbNG:form>

<!-- Display the B2 version -->
<small><i>Version: <%= ca.ubc.med.blackboard.b2template.Util.VERSION %></i></small>

</bbNG:learningSystemPage>