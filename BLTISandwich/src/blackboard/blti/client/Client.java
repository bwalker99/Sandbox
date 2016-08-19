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

//import org.imsglobal.lti.BasicLTIUtil;


import java.io.PrintWriter;

import blackboard.blti.message.*;
import blackboard.blti.consumer.*;


public class Client  extends HttpServlet {
	
	private String mysecret = "S3cr3t419s3nT8"; //"Manuj";
	
    public static final String BASICLTI_SUBMIT = "ext_basiclti_submit";
	
	private String[] planets = {
			"Mercury takes 59 days to make a rotation but only 88 days to circle the Sun.",
			"Venus is the brightest planet in our sky and can sometimes be seen with the naked eye.",
			"Earth has more exposed water than land.  Three quarters of the Earth is covered by water.",
			"Mars is the home of 'Olympus Mons', the largest volcano found in the solar system.",
			"Jupiter is the largest planet in the solar system. It spins very quickly on its axis.  A day on Jupiter lasts only 9 hours and 55 minutes.",
			"Saturn is the second biggest planet, but its also the lightest planet.  If there was a bathtub big enough to hold Saturn, it would float in the water. ",
			"Uranus axis is at a 97 degree angle, meaning that it orbits lying on its side.",
			"Neptune was discovered in 1846 (over 150 years ago).  Since that time it has still yet to make a complete orbit around the sun, because one Neptune year lasts 165 Earth years",
			"Plutos orbit sometimes brings it closer to the Sun than Neptune.  It jumped ahead of Neptune on September 5, 1989 and remained there until February, 1999 when it went back to being the farthest. "						
	};
	

		  public void doGet (HttpServletRequest request, HttpServletResponse response) 
		    throws ServletException, IOException  {
		    // processRequest(request, response);
		    postClient(request, response);
		  }

		  public void doPost (HttpServletRequest request, HttpServletResponse response) 
		    throws ServletException, IOException  {
		  	// processRequest(request,response);
		    postClient(request, response);
		  }
		  
		  private void postClient(HttpServletRequest request, HttpServletResponse response) {
			  		
			  
             String launchsite = "https://mdupusers.med.ubc.ca:8443/fomstudent-lti/login";
			    BLTIMessage msg = new BLTIMessage( "ConsumerKey" );
			    msg.getResourceLink().setId( "testResourceId" );
			    msg.getUser().setId( "medstu1" );
			    msg.getUser().setLisSourcedId("medstu1");
			    msg.getUser().setFullName( "Medicine Student One" );
			    msg.getUser().addRole( new Role( "Student" ) );
			    
			    BLTIConsumer consumer = new BLTIConsumer( "POST", launchsite,msg );
			    consumer.sign(mysecret);
			    List<Map.Entry<String, String>> launchParams = consumer.getParameters();
			    
			System.out.println("====================\nPosting to : " + launchsite);
			String output = null;
			Map<String,String> tempmap = new TreeMap<String,String>();
			for (Map.Entry<String,String> e : launchParams) { 
				tempmap.put(e.getKey(),e.getValue());
				System.out.println(e.getKey() + "=" + e.getValue());
			}
			output = postLaunchHTML(tempmap, launchsite, false);
	

			try {
				PrintWriter out = response.getWriter();
				out.println(output);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			    //... after this, create the <form> that autoposts
			    
		  }
		  
		  
		  private void processRequest(HttpServletRequest request, HttpServletResponse response) {
			  
		//	  showStuff(request);
			  String id = request.getParameter("id");
		  
		//	  System.out.println(this.getClass().getName() + "id=" + id);


			    int p = 0; 
			    if (id == null || id.length() == 0) {
			    	java.util.Random random = new java.util.Random();
			    	p = random.nextInt(9);
			    }
			    else 
			    	p = Integer.parseInt(id);

		//	  request.setAttribute("id", "" + p);
		//	  request.setAttribute("description", planets[p]);
			  
			    java.util.Enumeration E = request.getParameterNames();

			    Map<String,String> M = new HashMap<String,String>();
			    
			    while (E.hasMoreElements()) { 
				      String param = (String)E.nextElement();
				      M.put(param,request.getParameter(param));
				      }
			    					    	

				  response.setContentType("text/html");
				  try {
				    PrintWriter out = response.getWriter();
				    
				    out.println("<html><head><title>LTI Planet Test</title></head><body>");
				    out.println("<h3>LTI Planet Test</h3>Supplied ID value: " + id + "<br/><br/>");
				    
				 
				    	
				    p = 0; 
				    if (id == null || id.length() == 0) {
				    	java.util.Random random = new java.util.Random();
				    	p = random.nextInt(9);
				    }
				    else 
				    	p = Integer.parseInt(id);
				    	
				    out.println("<br/>Displaying info for planet " + p + "<br/>");
				    out.println("<h4>" + planets[p] + "</h4>");
				    out.println("</body></html>");
				    out.close();
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
		  
   /**
     * Copied from imsglobal code.
     *  
     * Create the HTML to render a POST form and then automatically submit it.
     * Make sure to call {@link #cleanupProperties(Properties)} before signing.
     *
     * @param cleanProperties Assumes you have called
     * {@link #cleanupProperties(Properties)} beforehand.
     * @param endpoint The LTI launch url.
     * @param debug Useful for viewing the HTML before posting to end point.
     * @return the HTML ready for IFRAME src = inclusion.
     */
		    public static String postLaunchHTML(
		            final Map<String, String> cleanProperties, String endpoint, boolean debug) {
		        if (cleanProperties == null || cleanProperties.isEmpty()) {
		            throw new IllegalArgumentException(
		                    "cleanProperties == null || cleanProperties.isEmpty()");
		        }
		        if (endpoint == null) {
		            throw new IllegalArgumentException("endpoint == null");
		        }
		        Map<String, String> newMap = null;
		        if (debug) {
		            // sort the properties for readability
		            newMap = new TreeMap<String, String>(cleanProperties);
		        } else {
		            newMap = cleanProperties;
		        }
		        StringBuilder text = new StringBuilder();
		        // paint form
		        text.append("<div id=\"ltiLaunchFormSubmitArea\">\n");
		        text.append("<form action=\"");
		        text.append(endpoint);
		        text.append("\" name=\"ltiLaunchForm\" id=\"ltiLaunchForm\" method=\"post\" ");
		        text.append(" encType=\"application/x-www-form-urlencoded\" accept-charset=\"utf-8\">\n");
		        for (Entry<String, String> entry : newMap.entrySet()) {
		            String key = entry.getKey();
		            String value = entry.getValue();
		            if (value == null) {
		                continue;
		            }
		            // This will escape the contents pretty much - at least
		            // we will be safe and not generate dangerous HTML
		     //       key = htmlspecialchars(key);
		     //       value = htmlspecialchars(value);
		            if (key.equals(BASICLTI_SUBMIT)) {
		                text.append("<input type=\"submit\" name=\"");
		            } else {
		                text.append("<input type=\"hidden\" name=\"");
		            }
		            text.append(key);
		            text.append("\" value=\"");
		            text.append(value);
		            text.append("\"/>\n");
		        }
		        text.append("</form>\n");
		        text.append("</div>\n");
		        
		        System.out.println("===SoFar===:" + text.toString());

	        
		        // Paint the auto-pop up if we are transitioning from https: to http:
		        // and are not already the top frame...
		        text.append("<script type=\"text/javascript\">\n");
		        text.append("if (window.top!=window.self) {\n");
		        text.append("  theform = document.getElementById('ltiLaunchForm');\n");
		        text.append("  if ( theform && theform.action ) {\n");
		        text.append("   formAction = theform.action;\n");
		        text.append("   ourUrl = window.location.href;\n");
		        text.append("   if ( formAction.indexOf('http://') == 0 && ourUrl.indexOf('https://') == 0 ) {\n");
		        text.append("      theform.target = '_blank';\n");
		        text.append("      window.console && console.log('Launching http from https in new window!');\n");
		        text.append("    }\n");
		        text.append("  }\n");
		        text.append("}\n");
		        text.append("</script>\n");
		        
		        System.out.println("===SoFar2===:" + text.toString());		        
		        // paint debug output
		        if (debug) {
		            text.append("<pre>\n");
		            text.append("<b>BasicLTI Endpoint</b>\n");
		            text.append(endpoint);
		            text.append("\n\n");
		            text.append("<b>BasicLTI Parameters:</b>\n");
		            for (Entry<String, String> entry : newMap.entrySet()) {
		                String key = entry.getKey();
		                String value = entry.getValue();
		                if (value == null) {
		                    continue;
		                }
		                // text.append(htmlspecialchars(key));
		                text.append(key);
		                text.append("=");
		                // text.append(htmlspecialchars(value));
		                text.append(value);
		                text.append("\n");
		            }
		            text.append("</pre>\n");
		        } else {
		            // paint auto submit script
		            text.append(" <script language=\"javascript\"> \n"
	                            + "	document.getElementById(\"ltiLaunchFormSubmitArea\").style.display = \"none\";\n"
		                            + "	nei = document.createElement('input');\n"
		                            + "	nei.setAttribute('type', 'hidden');\n"
		                            + "	nei.setAttribute('name', '"
		                            + BASICLTI_SUBMIT
		                            + "');\n"
		                            + "	nei.setAttribute('value', '"
		                            + newMap.get(BASICLTI_SUBMIT)
		                            + "');\n"
		                            + "	document.getElementById(\"ltiLaunchForm\").appendChild(nei);\n"
		                            + "	document.ltiLaunchForm.submit(); \n" + " </script> \n");
		        }

		        String htmltext = text.toString();
		        System.out.println("===SoFar3===:" + htmltext);		        
		        return htmltext;
		    }

	}

