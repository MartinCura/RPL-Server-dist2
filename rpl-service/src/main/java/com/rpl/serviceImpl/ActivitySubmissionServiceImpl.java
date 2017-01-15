package com.rpl.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rpl.exception.RplQueueException;
import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.QueueMessage;
import com.rpl.model.Status;
import com.rpl.persistence.ActivityDAO;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.service.ActivitySubmissionService;
import com.rpl.service.QueueService;
import com.rpl.service.UserService;
import com.rpl.service.util.JsonUtils;

@Stateless
public class ActivitySubmissionServiceImpl implements ActivitySubmissionService {
	
	@Inject
	private QueueService queueService;
	@Inject
	private UserService userService;
	@Inject
	private ActivitySubmissionDAO activitySubmissionDAO;
	@Inject
	private ActivityDAO activityDAO;
	

	public ActivitySubmission getSubmissionById(Long id) {
		return activitySubmissionDAO.find(id);
	}

	public void submit(Long activityId, ActivitySubmission submission) throws RplQueueException {
		Activity activity = activityDAO.find(activityId);
		submission.setPerson(userService.getCurrentUser());
		submission.setActivity(activity);
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

	public void markAsSelected(Long submissionId) {
		ActivitySubmission submission = activitySubmissionDAO.find(submissionId);
		submission.setSelected(true);
		activitySubmissionDAO.save(submission);
		//TODO si hay otra marcada ponerla en false
	}

	public List<ActivitySubmission> getSubmissionsByActivity(Long personId, Long activityId) {
		return activitySubmissionDAO.findByPersonAndActivity(personId, activityId);
	}

}