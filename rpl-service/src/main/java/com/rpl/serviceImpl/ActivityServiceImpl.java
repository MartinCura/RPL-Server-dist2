package com.rpl.serviceImpl;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.model.Activity;
import com.rpl.model.Language;
import com.rpl.model.TestType;
import com.rpl.persistence.ActivityDAO;
import com.rpl.service.ActivityService;

@Stateless
public class ActivityServiceImpl implements ActivityService {
	
	@Inject
	private ActivityDAO activityDAO;
	
	
	public Activity getActivityById(Long id) {
		return activityDAO.find(id);
	}


	public void submit(Long courseId, Activity activity) {
		activityDAO.save(activity);
		
	}
}