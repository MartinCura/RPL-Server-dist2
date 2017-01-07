package com.rpl.daemon;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.QueueMessage;
import com.rpl.model.runner.Result;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.persistence.ApplicationDAO;
import com.rpl.persistence.ResultDAO;
import com.rpl.service.QueueService;
import com.rpl.serviceImpl.QueueServiceImpl;

public class Daemon {
	
	public static void main( String[] args ){
		
		ApplicationDAO.setBeanTransactionManagement();
        
		QueueService qs = new QueueServiceImpl();
		ActivitySubmissionDAO activitySubmissionDAO = new ActivitySubmissionDAO();
		ResultDAO resultDAO = new ResultDAO();
		Tester tester = new Tester();
		boolean running = true;
    	
		while (running) {
			try {
				QueueMessage message = qs.receive();
				String submissionId = message.getMsg();
				ActivitySubmission submission = activitySubmissionDAO.find(Long.valueOf(submissionId));
				Result result = tester.runSubmission(submission);
				tester.analyzeResult(submission, result);
				result = resultDAO.save(result);
				submission.setResult(result);
				activitySubmissionDAO.save(submission);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
