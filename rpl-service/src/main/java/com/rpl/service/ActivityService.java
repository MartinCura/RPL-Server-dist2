package com.rpl.service;

import com.rpl.model.Activity;

public interface ActivityService {
	
	public Activity getActivityById(Long id);

	public void submit(Long courseId, Activity activity);

}
