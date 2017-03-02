package com.rpl.service;

import com.rpl.exception.RplException;
import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.exception.RplQueueException;
import com.rpl.model.ActivitySubmission;

import java.util.List;

public interface ActivitySubmissionService {
	
	public ActivitySubmission submit(Long activityId, ActivitySubmission submission) throws RplException;

	public ActivitySubmission getSubmissionById(Long id);

	public void markAsSelected(Long submissionId) throws RplException;

    public List<ActivitySubmission> getSubmissionsByActivity(Long activityId);

	public List<ActivitySubmission> getDefinitiveSubmissionsByActivity(Long activityId) throws RplException;

	public void queueSubmission(Long id) throws RplQueueException;

    public void markAsDefinitive(Long submissionId) throws RplException;

}
