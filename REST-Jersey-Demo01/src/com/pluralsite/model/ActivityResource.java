package com.pluralsite.model;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.pluralsite.repository.*;

@Path("activities")
public class ActivityResource {
private ActivityRepository actRepository = new ActivityRepositoryStub();


// Does work. When calling it with Header Content-Type: application/json and sending json as {"duration":15,"desc":"Swimming" }

@POST
@Path("activity")
// @Consumes(MediaType.APPLICATION_XML)   // changed from JSON in course
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON}) 
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public Activity createActivity(Activity activity) {
			
	System.out.println(this.getClass().getName() + ":" + activity.getDescription());
	System.out.println(this.getClass().getName() + ":" + activity.getDuration());
	
	Activity actreturn = actRepository.create(activity);
	
	return actreturn;
}



// Doesn't WORK!! Gives 
//<h1>HTTP Status 415 - Unsupported Media Type</h1>
//        <u>The server refused this request because the request entity is in a format not supported by the requested resource for the requested method.</u>
@POST
@Path("activity")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public Activity createActivityParams(MultivaluedMap<String,String> formParams) {
	
	System.out.println(formParams.getFirst("description"));
	System.out.println(formParams.getFirst("duration"));
	
	Activity act = new Activity();
	act.setDescription(formParams.getFirst("description"));
	act.setDuration(Integer.parseInt(formParams.getFirst("duration")));
	
	actRepository.create(act);
	
	return act;
}

@GET
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public List<Activity> getAllActivities() { 
	return actRepository.findAllActivities();	
}

/*
@GET
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("{activityId}")
public Activity getActivity(@PathParam ("activityId") String activityId) { 
	return actRepository.findActivity(activityId);	
}
*/

// Return a response object instead!
@GET
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("{activityId}")
public Response getActivity(@PathParam ("activityId") String activityId) {
	if (activityId == null || activityId.length() < 4) { 
		return Response.status(Status.BAD_REQUEST).build();
	}
	Activity activity =  actRepository.findActivity(activityId);
	if (activity == null) { 
		return Response.status(Status.NOT_FOUND).build();
	}
	return Response.ok().entity(activity).build();
}




@GET
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("{activityId}/user")
public User getUser(@PathParam ("activityId") String activityId) { 
	return actRepository.findActivity(activityId).getUser();	
}

}
