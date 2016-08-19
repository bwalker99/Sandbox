/*
 * Copyright (C) 2011, Blackboard Inc.
 * 
 * All rights reserved. 
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 
 * -- Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 * 
 * -- Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * -- Neither the name of Blackboard Inc. nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY BLACKBOARD INC ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL BLACKBOARD INC. BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * For use in receiving Basic LTI requests on the Tool Provider side.
 * 
 * @author Jim Riecken <jriecken@blackboard.com>
 */
package blackboard.blti.provider;

import static blackboard.blti.message.BLTIParameter.*;

import blackboard.blti.message.*;
import blackboard.blti.util.StringUtil;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.oauth.*;
import net.oauth.server.OAuthServlet;

public class BLTIProvider
{
  /**
   * Get a BasicLTI message off of the request.
   * 
   * @param request The current request.
   * @return The BasicLTI message from the request.
   */
  public static BLTIMessage getMessage( HttpServletRequest request )
  {
    OAuthMessage msg = OAuthServlet.getMessage( request, null );
    BLTIMessage result = new BLTIMessage( msg );
    result.setMessageType( request.getParameter( MESSAGE_TYPE ) );
    result.setLtiVersion( request.getParameter( LTI_VERSION ) );

    ResourceLink resourceLink = result.getResourceLink();
    resourceLink.setId( request.getParameter( RESOURCE_LINK_ID ) );
    resourceLink.setTitle( request.getParameter( RESOURCE_LINK_TITLE ) );
    resourceLink.setDescription( request.getParameter( RESOURCE_LINK_DESCRIPTION ) );

    User user = result.getUser();
    user.setId( request.getParameter( USER_ID ) );
    user.setImageUrl( request.getParameter( USER_IMAGE ) );
    String roles = request.getParameter( ROLES );
    if ( StringUtil.notEmpty( roles ) )
    {
      for ( String role : roles.split( "," ) )
      {
        user.addRole( new Role( role ) );
      }
    }
    user.setLisSourcedId( request.getParameter( LIS_PERSON_SOURCEDID ) );
    user.setGivenName( request.getParameter( LIS_PERSON_NAME_GIVEN ) );
    user.setFamilyName( request.getParameter( LIS_PERSON_NAME_FAMILY ) );
    user.setFullName( request.getParameter( LIS_PERSON_NAME_FULL ) );
    user.setEmail( request.getParameter( LIS_PERSON_CONTACT_EMAIL_PRIMARY ) );

    Context context = result.getContext();
    context.setId( request.getParameter( CONTEXT_ID ) );
    context.setType( request.getParameter( CONTEXT_TYPE ) );
    context.setTitle( request.getParameter( CONTEXT_TITLE ) );
    context.setLabel( request.getParameter( CONTEXT_LABEL ) );
    context.setLisCourseOfferingSourcedId( request.getParameter( LIS_COURSE_OFFERING_SOURCEDID ) );
    context.setLisCourseSectionSourcedId( request.getParameter( LIS_COURSE_SECTION_SOURCEDID ) );

    LaunchPresentation launchPresentation = result.getLaunchPresentation();
    launchPresentation.setLocale( request.getParameter( LAUNCH_PRESENTATION_LOCALE ) );
    launchPresentation.setDocumentTarget( request.getParameter( LAUNCH_PRESENTATION_DOCUMENT_TARGET ) );
    launchPresentation.setWidth( request.getParameter( LAUNCH_PRESENTATION_WIDTH ) );
    launchPresentation.setHeight( request.getParameter( LAUNCH_PRESENTATION_HEIGHT ) );
    launchPresentation.setReturnUrl( request.getParameter( LAUNCH_PRESENTATION_RETURN_URL ) );

    ToolConsumerInfo toolConsumerInfo = result.getToolConsumerInfo();
    toolConsumerInfo.setGuid( request.getParameter( TOOL_CONSUMER_INSTANCE_GUID ) );
    toolConsumerInfo.setName( request.getParameter( TOOL_CONSUMER_INSTANCE_NAME ) );
    toolConsumerInfo.setDescription( request.getParameter( TOOL_CONSUMER_INSTANCE_DESCRIPTION ) );
    toolConsumerInfo.setUrl( request.getParameter( TOOL_CONSUMER_INSTANCE_URL ) );
    toolConsumerInfo.setEmail( request.getParameter( TOOL_CONSUMER_INSTANCE_CONTACT_EMAIL ) );

    Map<String, String> customParameters = result.getCustomParameters();
    @SuppressWarnings( "unchecked" )
    Enumeration<String> paramNames = request.getParameterNames();
    while ( paramNames.hasMoreElements() )
    {
      String name = paramNames.nextElement();
      if ( name.startsWith( CUSTOM ) )
      {
        customParameters.put( name.substring( CUSTOM.length() ), request.getParameter( name ) );
      }
    }

    return result;
  }

  /**
   * Check whether the specified message is valid, checking the signature with the specified secret.
   * 
   * @param msg The message to validate.
   * @param secret The secret to use in signature computation.
   * @return Whether the message is valid.
   */
  public static boolean isValid( BLTIMessage msg, String secret )
  {
    try
    {
      String key = msg.getKey();
      if ( StringUtil.notEmpty( secret ) )
      {
        OAuthValidator validator = new SimpleOAuthValidator();
        OAuthConsumer consumer = new OAuthConsumer( "about:blank", key, secret, null );
        validator.validateMessage( msg.getOAuthMessage(), new OAuthAccessor( consumer ) );
      }

      // Check required BLTI parameters.
      if ( !MESSAGE_TYPE_VALUE.equals( msg.getMessageType() ) || !LTI_VERSION_VALUE.equals( msg.getLtiVersion() )
           || StringUtil.isEmpty( msg.getResourceLink().getId() ) || StringUtil.isEmpty( key ) )
      {
        return false;
      }

      return true;
    }
    catch ( Exception e )
    {
      return false;
    }
  }
}
