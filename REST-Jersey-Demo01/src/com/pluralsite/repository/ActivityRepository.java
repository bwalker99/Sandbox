package com.pluralsite.repository;

import java.util.List;

import com.pluralsite.model.Activity;

public interface ActivityRepository {

	public abstract List<Activity> findAllActivities();

	public abstract Activity findActivity(String activityId);

	public abstract Activity create(Activity act);

}