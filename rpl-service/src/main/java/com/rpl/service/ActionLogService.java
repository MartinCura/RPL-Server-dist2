package com.rpl.service;

import com.rpl.model.Person;

public interface ActionLogService {

	public void logLogin(Person p);

	public void logLogout();
	
	public void logActivitySubmission(Long activitySubmissionId);
}
