package com.pluralsite.repository;

import java.util.List;
import java.util.ArrayList;

import com.pluralsite.model.*;

public class ActivityRepositoryStub implements ActivityRepository {

		List<Activity> activities = new ArrayList<Activity>();

		public ActivityRepositoryStub() {
			Activity act1 = new Activity();
			
			act1.setId("4321");
			act1.setDescription("Swimming");
			act1.setDuration(55);
			activities.add(act1);
			
			User user = new User();
			user.setId(act1.getId());
			user.setFirstname("Joe");
			user.setLastname("Smith");
			act1.setUser(user);
			
			Activity act2 = new Activity();
			act2.setId("1234");   
			act2.setDescription("Cycling");
			act2.setDuration(120);
			

			user = new User();
			user.setId(act2.getId());
			user.setFirstname("Sam");
			user.setLastname("spade");
			act2.setUser(user);
			activities.add(act2);
	
		}
		
	/* (non-Javadoc)
	 * @see com.pluralsite.repository.ActivitiesRespository#findAllActivities()
	 */
	@Override
	public List<Activity> findAllActivities() { 

		/*
		Activity act1 = new Activity();
		
		act1.setId("4321");
		act1.setDescription("Swimming");
		act1.setDuration(55);
		activities.add(act1);
		
		User user = new User();
		user.setId(act1.getId());
		user.setFirstname("Joe");
		user.setLastname("Smith");
		act1.setUser(user);
		
		Activity act2 = new Activity();
		act2.setId("1234");   
		act2.setDescription("Cycling");
		act2.setDuration(120);
		

		user = new User();
		user.setId(act2.getId());
		user.setFirstname("Sam");
		user.setLastname("spade");
		act2.setUser(user);
		activities.add(act2);
		*/
		return activities;
		
	}

	@Override
	public Activity findActivity(String activityId) {
		System.out.println("Looking for activity: " + activityId);
		if (activityId.equals("7777"))
			return null;
		
		Activity act2 = null;
		
		for (Activity act : activities) { 
			System.out.println(" -->Activity: " + act.getId() + " " + act.getDescription());
			if (act.getId().equals(activityId)) { 
				act2 = act;
				break;
			}
		}
		 				
		return act2;
	}

	@Override
	public void create(Activity act) {
		activities.add(act);
		
	}
	

}
