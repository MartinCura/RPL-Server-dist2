package com.rpl.serviceImpl;

import javax.inject.Inject;

import com.rpl.model.Activity;
import com.rpl.persistence.ActivityDAO;
import com.rpl.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
	
	@Inject
	private ActivityDAO activityDAO;
	
	
	public Activity getActivityById(Long id) {
		return activityDAO.find(id);
	}
}