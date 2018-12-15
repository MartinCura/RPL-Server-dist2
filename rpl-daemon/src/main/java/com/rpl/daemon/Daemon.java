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

/**
 * Daemon worker: runs activity submission code sent to the specified RMQ queue, then sends the result to another queue
 * for persistence. Note that it works with a partial copy of the ActivitySubmission, as certain fields are ignored by
 * the JSON serializer.
**/
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
		///ActivitySubmissionDAO activitySubmissionDAO = new ActivitySubmissionDAO();//???
		//ResultDAO resultDAO = new ResultDAO();
		Tester tester = new Tester();

		boolean running = true;
		while (running) {
			try {

				QueueMessage message = qs_subm.receive();
				String submissionString = message.getMsg();
				System.out.println("submissionString:");//
				System.out.println(submissionString);//
				///activitySubmissionDAO.clear();//???

                ActivitySubmission submission = JsonUtils.jsonToObject(submissionString, ActivitySubmission.class);
                if (submission == null) {
                    System.out.println("ERROR al decodificar una submission desde JSON");
                    continue;
                }

                Result result = tester.runSubmission(submission);
				//tester.analyzeResult(submission, result);
                //result.analyze(submission);
                //result.setIds(submission.getId());
                //if (result.getTests() != null) {
                //    result.getTests().fixTestsResults();
                //}
//                submission.setResult(result);
//                submission.analyzeResult();

				try {
                    // Send to monitor for persistence
//					String resultSubmJson = JsonUtils.objectToJson(submission);
                    String resultJson = JsonUtils.objectToJson(result);
					System.out.println("resultJson:");//
					System.out.println(resultJson);//
					QueueMessage qm = new QueueMessage(resultJson);
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

			} catch (IOException | InterruptedException | TimeoutException e) {
				e.printStackTrace(); // ToDo: better error message?
            } catch (Exception e) {
			    System.err.println("UNEXPECTED ERROR");
			    e.printStackTrace();
			}
		}
	}
}
