package blackboard.blti.client;

import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.imsglobal.lti.*;
import org.imsglobal.lti.launch.*;

import java.io.PrintWriter;

import blackboard.blti.message.*;
import blackboard.blti.consumer.*;


public class Client2  extends HttpServlet {
	
	private String mysecret = "S3cr3t419s3nT8"; //"Manuj";
	
    public static final String BASICLTI_SUBMIT = "ext_basiclti_submit";
	
	

		  public void doGet (HttpServletRequest request, HttpServletResponse response) 
		    throws ServletException, IOException  {

		    postClient(request, response);
		  }

		  public void doPost (HttpServletRequest request, HttpServletResponse response) 
		    throws ServletException, IOException  {
		    postClient(request, response);
		  }
		  
		  private void postClient(HttpServletRequest request, HttpServletResponse response) {
			  		
			  
             String launchsite = "https://mdupusers.med.ubc.ca:8443/fomstudent-lti/login";
             
             Map<String,String> parameters = new TreeMap<String,String>();
                parameters.put(BasicLTIConstants.RESOURCE_LINK_ID, "BasicLTIResourceId");
			    // msg.getResourceLink().setId( "testResourceId" );
                parameters.put(BasicLTIConstants.USER_ID,"medstu1");
			    // msg.getUser().setId( "medstu1" );
                parameters.put(BasicLTIConstants.LIS_PERSON_SOURCEDID,"medtu1");
			    // msg.getUser().setLisSourcedId("medstu1");
                parameters.put(BasicLTIConstants.LIS_PERSON_NAME_FULL,"Medicine Student One" );
			    // msg.getUser().setFullName( "Medicine Student One" );
                parameters.put(BasicLTIConstants.ROLES,"Learner");
			    // msg.getUser().addRole( new Role( "Learner" ) );
			    
                parameters.put(BasicLTIConstants.CONTEXT_TYPE_COURSE_OFFERING,"CourseOffering");
			    // msg.getContext().setType("CourseOffering");
                parameters.put(BasicLTIConstants.TOOL_CONSUMER_INSTANCE_DESCRIPTION,"FomStudentBasicLTI");
			    // msg.getToolConsumerInfo().setDescription("FoMStudent");
                
                parameters.put(BasicLTIConstants.TOOL_CONSUMER_INSTANCE_CONTACT_EMAIL,"medit.elearningios@ubc.ca");
			    // msg.getToolConsumerInfo().setEmail("medit.elearningios@ubc.ca");
                
                parameters.put(BasicLTIConstants.TOOL_CONSUMER_INSTANCE_URL,"http://localhost:8080/bltisandwich");
			    // msg.getToolConsumerInfo().setUrl("http://localhost:8080/bltisandwich");
                
                parameters.put(BasicLTIConstants.TOOL_CONSUMER_INSTANCE_NAME,"FoMStudent");
			    // msg.getToolConsumerInfo().setName("FoMStudent");
                
                parameters.put(BasicLTIConstants.TOOL_CONSUMER_INSTANCE_GUID,"0a6fd1b7-1258-48e3-ad41-7a0249aeb83a");
			    // msg.getToolConsumerInfo().setGuid("0a6fd1b7-1258-48e3-ad41-7a0249aeb83a");
                
                parameters.put(BasicLTIConstants.LAUNCH_PRESENTATION_DOCUMENT_TARGET,"window");
			    // msg.getLaunchPresentation().setDocumentTarget("window");
                
                parameters.put(BasicLTIConstants.LAUNCH_PRESENTATION_LOCALE,"en_US");
			    // msg.getLaunchPresentation().setLocale("en_US");
                
                parameters.put(BasicLTIConstants.LAUNCH_PRESENTATION_RETURN_URL,"http://localhost:8080/bltisandwich/test.html");
			    // msg.getLaunchPresentation().setReturnUrl("http://localhost:8080/bltisandwich/test.html");
                
                parameters.put(BasicLTIConstants.CONTEXT_ID,"99dd04aa5b5e4514815d7122959bc6aa");
			    // msg.getContext().setId("99dd04aa5b5e4514815d7122959bc6aa");
			    			                    
			    
			System.out.println("====================\nPosting to : " + launchsite);
			
			LtiSigner ltiSigner = new LtiOauthSigner();
			try {
			    Map<String, String> signedParameters = ltiSigner.signParameters(parameters, "ConsumerKey", mysecret, launchsite, "POST");
			    String output = BasicLTIUtil.postLaunchHTML(signedParameters,launchsite,false);
				PrintWriter out = response.getWriter();
				out.println(output);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
             			
			    
		  }
		  
	

		  /** 
		   * Handy. Shows a lot of the servlet objects available
		   */
		  private void showStuff(HttpServletRequest request) {
		  	System.out.println(this.getClass().getName() +":getContextPath=   " + request.getContextPath());
		  	System.out.println(this.getClass().getName() +":getPathInfo=      " + request.getPathInfo());
		  	System.out.println(this.getClass().getName() +":getPathTranslated=" + request.getPathTranslated());
		  	System.out.println(this.getClass().getName() +":getQueryString=   " + request.getQueryString());
		  	System.out.println(this.getClass().getName() +":getRequestURI=    " + request.getRequestURI());
//		  	System.out.println(this.getClass().getName() +":getRequestURL=    " + request.getRequestURL());	
		  	System.out.println(this.getClass().getName() +":getServletPath=   " + request.getServletPath());
		    System.out.println(this.getClass().getName() +"Parameters:");
		    java.util.Enumeration E = request.getParameterNames();
		    while (E.hasMoreElements()) { 
		      String param = (String)E.nextElement();
		      System.out.println("  " + param + "=" + request.getParameter(param));
		      }
		  }
		  

	}

