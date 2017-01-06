package com.rpl.serviceImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.model.Activity;
import com.rpl.persistence.ActivityDAO;
import com.rpl.service.ActivityService;

@Stateless
public class ActivityServiceImpl implements ActivityService {
	
	@Inject
	private ActivityDAO activityDAO;
	
	
	public Activity getActivityById(Long id) {
		return activityDAO.find(id);
	}

	public List<Activity> getActivitiesByCourse(Long courseId) {
		return activityDAO.findByCourse(courseId);
	}

	public List<Activity> getActivitiesByTopic(Long topicId) {
		return activityDAO.findByTopic(topicId);
	}


	public void submit(Long courseId, Activity activity) {
		activityDAO.save(activity);
		
	}
}