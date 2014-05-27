<%@ taglib uri="/bbNG" prefix="bbNG"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Imports all the Blackboard libraries. Probably don't need them all. --> 
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
<!--  Specific third party library.  -->
<%@ page import="com.spvsoftwareproducts.blackboard.utils.B2Context"  errorPage="../error.jsp"%>

<!--  the includePage is a simple non-fancy formatted page -->
<bbNG:includedPage ctxId="ctx" >
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
    String userName = sessionUser.getUserName();
    String givenName = sessionUser.getGivenName();
    String familyName = sessionUser.getFamilyName();
    pageContext.setAttribute("myusername",givenName + " " + familyName + "(" + userName + ")");
    
%>
	
<h2>UBC FoM B2 Template</h2>	
You are logged in as: <%= givenName %><br/> <!-- Note. This displays the java variable defined above. -->
Click submit below for more information. 

    <bbNG:form action="${launchUrl}" method="POST" id="id_userform" name="UserForm" >

     <bbNG:dataCollection showSubmitButtons="true" >
                      
     <bbNG:step title="Click for more information">

      <bbNG:dataElement  label="Username">
        <bbNG:textElement name="username" size="50" value="${myusername}" displayOnly="true"/> <!-- This displays the jstl variable. -->
      </bbNG:dataElement>     
     
    </bbNG:step>
    
          
   <bbNG:stepSubmit>
    <bbNG:stepSubmitButton label="Submit" />
   </bbNG:stepSubmit>
   
  </bbNG:dataCollection>
</bbNG:form>


</bbNG:includedPage>