package com.pluralsite.client;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

import com.pluralsite.client.ActivityClient;
import com.pluralsite.model.Activity;

public class ActivityClientTest {
	
	@Test
	public void testCreate() { 
		ActivityClient client = new ActivityClient();
		Activity activity = new Activity();
		activity.setDescription("Canoeing");
		activity.setDuration(90);
		
		activity = client.create(activity);
		assertNotNull(activity);
				
	}

	@Test
	public void testGet() {
		ActivityClient client = new ActivityClient();
		Activity activity = client.get("xxxx");
		System.out.println(activity.getId() + " " + activity.getDuration() + " " + activity.getDescription());
		
		assertNotNull(activity);
	}

	@Test
	public void testGetList() {
		ActivityClient client = new ActivityClient();
		List<Activity> activities = client.get();		
		
		assertNotNull(activities);
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetWithBadRequest() { 
		ActivityClient client = new ActivityClient();
		client.get("xx"); // Don't need an assert
		
	}
	
	
	@Test(expected=RuntimeException.class)
	public void testGetWithNotFound() { 
		ActivityClient client = new ActivityClient();
		client.get("7777");
		
	}
	
}
