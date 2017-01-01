package com.rpl.service;

import com.rpl.model.Activity;

import java.util.List;

public interface ActivityService {
	
	public Activity getActivityById(Long id);
	public List<Activity> getActivitiesByCourse(Long courseId);
	public List<Activity> getActivitiesByTopic(Long topicId);

	public void submit(Long courseId, Activity activity);

}
