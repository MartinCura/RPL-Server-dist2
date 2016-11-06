package com.rpl.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rpl.exception.RplQueueException;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.QueueMessage;
import com.rpl.model.Status;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.service.QueueService;
import com.rpl.service.SolutionService;
import com.rpl.service.util.JsonUtils;

@Stateless
public class SolutionServiceImpl implements SolutionService {

	@Inject
	private QueueService queueService;

	@Inject
	private ActivitySubmissionDAO activitySubmissionDAO;
	
	public void submit(Long id, ActivitySubmission submission) throws RplQueueException {

		submission.setSubmissionDate(new Date());
		submission.setStatus(Status.PENDING);
		submission = activitySubmissionDAO.save(submission);
		
		QueueMessage qm;
		try {
			qm = new QueueMessage(JsonUtils.objectToJson(submission.getId()));
			queueService.send(qm);
		} catch (JsonProcessingException e) {
			// No deberia suceder nunca
			e.printStackTrace();
		} catch (IOException e) {
			throw new RplQueueException(e);
		} catch (TimeoutException e) {
			throw new RplQueueException(e);
		}

	}

}
