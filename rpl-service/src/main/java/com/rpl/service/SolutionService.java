package com.rpl.service;

import com.rpl.exception.RplQueueException;
import com.rpl.model.ActivitySubmission;

public interface SolutionService {
	
	public void submit(Long id, ActivitySubmission submission) throws RplQueueException;

}
