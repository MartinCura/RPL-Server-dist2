package com.rpl.service;

import com.rpl.exception.RplQueueException;
import com.rpl.model.ActivitySubmission;

public interface ActivitySubmissionService {
	
	public void submit(Long activityId, ActivitySubmission submission) throws RplQueueException;

	public ActivitySubmission getSubmissionById(Long id);

}
