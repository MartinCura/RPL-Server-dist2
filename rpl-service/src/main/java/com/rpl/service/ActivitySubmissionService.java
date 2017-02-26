package com.rpl.service;

import com.rpl.exception.RplQueueException;
import com.rpl.model.ActivitySubmission;

import java.util.List;

public interface ActivitySubmissionService {
	
	public ActivitySubmission submit(Long activityId, ActivitySubmission submission);

	public ActivitySubmission getSubmissionById(Long id);

	public void markAsSelected(Long submissionId);

    public List<ActivitySubmission> getSubmissionsByActivity(Long activityId);

	public void queueSubmission(Long id) throws RplQueueException;

    public void markAsDefinitive(Long submissionId);
}
