package com.rpl.service;

import com.rpl.exception.RplQueueException;
import com.rpl.model.ActivitySubmission;

import java.util.List;

public interface ActivitySubmissionService {
	
	public void submit(Long activityId, ActivitySubmission submission) throws RplQueueException;

	public ActivitySubmission getSubmissionById(Long id);

	public void markAsSelected(Long submissionId);

    public List<ActivitySubmission> getSubmissionsByActivity(Long activityId);
}
