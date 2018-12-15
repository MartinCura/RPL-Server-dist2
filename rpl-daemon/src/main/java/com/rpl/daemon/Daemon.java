package com.rpl.daemon;

import com.fasterxml.jackson.core.JsonProcessingException;
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
		
		ApplicationDAO.setBeanTransactionManagement();// necesario??? romperá al distribuir?

		QueueConsumerService qs_subm;
		QueueService qs_res;
		try {
			qs_subm = new QueueConsumerServiceImpl();
			qs_res  = new QueueServiceImpl("rpl-res");
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
				System.out.println("submissionString:");//
				System.out.println(submissionString);//
				activitySubmissionDAO.clear();
				//ActivitySubmission submission = activitySubmissionDAO.find(Long.valueOf(submissionId));//QUI

                ActivitySubmission submission = JsonUtils.jsonToObject(submissionString, ActivitySubmission.class);
                if (submission == null) {
                    System.out.println("ERROR al decodificar una submission desde JSON");
                    continue;
                }

                Result result = tester.runSubmission(submission);
				tester.analyzeResult(submission, result);
				result.setIds(submission.getId());
				if (result.getTests() != null) {
                    result.getTests().fixTestsResults();
                }

				//result = resultDAO.save(result);
				//submission.setResult(result);
				submission.setResult(result);
				//activitySubmissionDAO.save(submission);

				try {
                    // Send to monitor for persistence
					String resultSubmJson = JsonUtils.objectToJson(submission);
					System.out.println("resultSubmJson:");//
					System.out.println(resultSubmJson);//
					QueueMessage qm = new QueueMessage(resultSubmJson);
					qs_res.send(qm);

					qs_subm.confirmReceive();
					// ToDo: Ack negativo de que no pudo ser procesado en caso de errores?
				} catch (JsonProcessingException e) {
					System.out.println("Error al codificar JSON de ActivitySubmission");
					//System.out.println(submissionString);//
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			//} catch (Exception e) {
			} catch (IOException | InterruptedException | TimeoutException e) {
				e.printStackTrace(); // ToDo: better error message
			}
		}
	}
}
