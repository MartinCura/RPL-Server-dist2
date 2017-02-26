package com.rpl.service;

import com.rpl.model.Person;

public interface ActionLogService {

	public void logLogin(Person p);

	public void logLogout();
	
	public void logPasswordUpdate();
	
	public void logActivitySubmission(Long activitySubmissionId);

	public void logMarkActivitySubmissionAsSelected(Long submissionId);

	public void logMarkActivitySubmissionAsDefinitive(Long submissionId);

	public void logNewUserRegistered(Person p);

	public void logDeletedActivity(Long id);

	public void logNewCourse(Long id);

	public void logDeletedCourse(Long id);

	public void logJoinedCourse(Long courseId, Long personId);

	public void logLeftCourse(Long courseId, Long personId);

	public void logNewActivity(Long id);
}
