package com.rpl.daemon;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.QueueMessage;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.service.QueueService;
import com.rpl.serviceImpl.QueueServiceImpl;

public class Daemon {
	
	public static void main( String[] args ){
        
		QueueService qs = new QueueServiceImpl();
		ActivitySubmissionDAO activitySubmissionDAO = new ActivitySubmissionDAO();
		Tester tester = new Tester();
		boolean running = true;
    	
		while (running) {
			try {
				QueueMessage message = qs.receive();
				String submissionId = message.getMsg();
				ActivitySubmission submission = activitySubmissionDAO.find(Long.valueOf(submissionId));

				String result = tester.runSubmission(submission);
				tester.analyzeResult(submission, result);
				
				activitySubmissionDAO.save(submission);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
