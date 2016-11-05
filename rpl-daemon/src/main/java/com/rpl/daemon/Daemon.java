package com.rpl.daemon;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.QueueMessage;
import com.rpl.service.QueueService;
import com.rpl.serviceImpl.QueueServiceImpl;

public class Daemon {
	
	public static void main( String[] args ){
        
		QueueService qs = new QueueServiceImpl();
		Tester tester = new Tester();
		boolean running = true;
    	
		while (running) {
			try {
				QueueMessage message = qs.receive();
				String submissionId = message.getMsg();
				//TODO buscar la submission de la db
				ActivitySubmission submission = new ActivitySubmission();

				String output = tester.runSubmission(submission);
				String result = tester.analyzeOutput(submission, output);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
