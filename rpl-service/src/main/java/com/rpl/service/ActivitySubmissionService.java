package com.rpl.service;

import com.rpl.exception.RplQueueException;
import com.rpl.model.ActivitySubmission;

public interface ActivitySubmissionService {
	
	public void submit(Long activityId, String code) throws RplQueueException;

	public ActivitySubmission getSubmissionById(Long id);

}
