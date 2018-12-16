package com.rpl.service;

import java.util.List;

import com.rpl.exception.RplException;
import com.rpl.exception.RplQueueException;
import com.rpl.model.ActivitySubmission;

public interface ActivitySubmissionService {
	
	ActivitySubmission submit(Long activityId, ActivitySubmission submission) throws RplException;

	ActivitySubmission getSubmissionById(Long id);

	void markAsSelected(Long submissionId) throws RplException;

    List<ActivitySubmission> getSubmissionsByActivity(Long activityId);
    
	List<ActivitySubmission> getSubmissionsByActivity(Long id, Long activityId);

	List<ActivitySubmission> getDefinitiveSubmissionsByActivity(Long activityId) throws RplException;

	//public void queueSubmission(Long id) throws RplQueueException;
    void queueSubmission(ActivitySubmission submission) throws RplQueueException;

	//public void queueSubmissionWithResult(ActivitySubmission submission) throws RplQueueException;

    void markAsDefinitive(Long submissionId) throws RplException;

}
