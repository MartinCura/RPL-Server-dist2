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
		
		QueueConsumerService qs_subm;
		QueueService qs_res;
		try {
			qs_subm = new QueueConsumerServiceImpl();
			qs_res  = new QueueServiceImpl("rpl-res");
		} catch (TimeoutException | IOException e) {	// ToDo: Emprolijar
			e.printStackTrace();
			return;
		}
		Tester tester = new Tester();

		boolean running = true;
		while (running) {
			try {

				QueueMessage message = qs_subm.receive();

				String submissionString = message.getMsg();
                ActivitySubmission submission = JsonUtils.jsonToObject(submissionString, ActivitySubmission.class);
                if (submission == null) {
                    System.out.println("ERROR al deserializar una submission desde JSON");
                    continue;
                }

                Result result = tester.runSubmission(submission);

				try {
                    // Send to monitor for persistence
                    String resultJson = JsonUtils.objectToJson(result);
					QueueMessage qm = new QueueMessage(resultJson);
					qs_res.send(qm);

					qs_subm.confirmReceive();
					// ToDo: Ack negativo de que no pudo ser procesado en caso de errores?
				} catch (JsonProcessingException e) {
					System.out.println("Error al serializar Result en JSON");
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (IOException | InterruptedException | TimeoutException e) {
				e.printStackTrace(); // ToDo: better error message?
            } catch (Exception e) {
			    System.err.println("ERROR INESPERADO");
			    e.printStackTrace();
			}
		}
	}
}
