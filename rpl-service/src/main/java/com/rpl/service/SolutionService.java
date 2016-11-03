package com.rpl.service;

import com.rpl.exception.RplQueueException;
import com.rpl.model.SolutionSubmission;

public interface SolutionService {
	
	public void submit(Long id, SolutionSubmission submission) throws RplQueueException;

}
