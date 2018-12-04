package com.rpl.daemon;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.QueueMessage;
import com.rpl.model.runner.Result;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.persistence.ApplicationDAO;
import com.rpl.service.QueueConsumerService;
import com.rpl.service.QueueService;
import com.rpl.service.util.JsonUtils;
import com.rpl.serviceImpl.QueueConsumerServiceImpl;
import com.rpl.serviceImpl.QueueServiceImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Daemon {

	public static void main( String[] args ){
		
		ApplicationDAO.setBeanTransactionManagement();

		QueueConsumerService qs_subm;
		QueueService qs_res;
		try {
			qs_subm = new QueueConsumerServiceImpl();
			qs_res  = new QueueServiceImpl("rpl_res");
		} catch (TimeoutException | IOException e) {	// ToDo: Emprolijar
			e.printStackTrace();
			return;
		}
		ActivitySubmissionDAO activitySubmissionDAO = new ActivitySubmissionDAO();
		//ResultDAO resultDAO = new ResultDAO();
		Tester tester = new Tester();
		boolean running = true;

		while (running) {
			try {
				QueueMessage message = qs_subm.receive();
				//String submissionId = message.getMsg();//QUI
				String submissionString = message.getMsg();
				activitySubmissionDAO.clear();
				//ActivitySubmission submission = activitySubmissionDAO.find(Long.valueOf(submissionId));//QUI
				try {
					ActivitySubmission submission = JsonUtils.jsonToObject(submissionString, ActivitySubmission.class);
					if (submission == null) {
						System.out.println("Error al decodificar una submission desde JSON");
						continue;
					}
					Result result = tester.runSubmission(submission);
					tester.analyzeResult(submission, result);
					result.setIds(submission.getId());
					if (result.getTests() != null)
						result.getTests().fixTestsResults();

					//result = resultDAO.save(result);
					//submission.setResult(result);
					submission.setResult(result);
					//activitySubmissionDAO.save(submission);

					// Send to monitor for persistence
					try {
						QueueMessage qm = new QueueMessage(JsonUtils.objectToJson(submission));
						qs_res.send(qm);

						qs_subm.confirmReceive();
						// ToDo: Devolver que no pudo ser procesado en caso de errores?
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					}
 				} catch (JsonGenerationException | JsonMappingException e) {
					System.out.println("Error al decodificar Json como ActivitySubmission");
					System.out.println(submissionString);
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
