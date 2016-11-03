package com.rpl.serviceImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rpl.exception.RplQueueException;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.QueueMessage;
import com.rpl.service.QueueService;
import com.rpl.service.SolutionService;
import com.rpl.service.util.JsonUtils;

public class SolutionServiceImpl implements SolutionService {

	@Inject
	QueueService queueService;

	public void submit(Long id, ActivitySubmission submission) throws RplQueueException {

		QueueMessage qm;
		try {
			qm = new QueueMessage(JsonUtils.objectToJson(submission));
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
