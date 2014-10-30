package com.pluralsite.model;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.pluralsite.repository.*;

@Path("activities")
public class ActivityResource {
private ActivityRepository actRepository = new ActivityRepositoryStub();

@POST
@Path("activity")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
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

@GET
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("{activityId}")
public Activity getActivity(@PathParam ("activityId") String activityId) { 
	return actRepository.findActivity(activityId);	
}

@GET
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Path("{activityId}/user")
public User getUser(@PathParam ("activityId") String activityId) { 
	return actRepository.findActivity(activityId).getUser();	
}

}
