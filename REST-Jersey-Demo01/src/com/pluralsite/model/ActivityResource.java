package com.pluralsite.model;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.pluralsite.repository.*;

@Path("activities")
public class ActivityResource {
private ActivityRepository actRepository = new ActivityRepositoryStub();

@GET
@Produces(MediaType.APPLICATION_XML)
public List<Activity> getAppActivities() { 
	return actRepository.findAllActivities();	
}
	
}
