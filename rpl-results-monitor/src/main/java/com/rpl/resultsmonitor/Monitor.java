package com.rpl.resultsmonitor;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.QueueMessage;
import com.rpl.model.runner.Result;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.persistence.ResultDAO;
import com.rpl.service.QueueConsumerService;
import com.rpl.service.util.JsonUtils;
import com.rpl.serviceImpl.QueueConsumerServiceImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Monitor {
    public static void main(String[] args) {
        //ApplicationDAO.setBeanTransactionManagement(); //no la creo necesaria

        QueueConsumerService qs;
        try {
            qs = new QueueConsumerServiceImpl();
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
            return;
        }
        ActivitySubmissionDAO activitySubmissionDAO = new ActivitySubmissionDAO();
        ResultDAO resultDAO = new ResultDAO();
        boolean running = true;

        while (running) {
            try {
                QueueMessage message = qs.receive();
                String submissionString = message.getMsg();
                activitySubmissionDAO.clear();

                try {
                    ActivitySubmission submission = JsonUtils.jsonToObject(submissionString, ActivitySubmission.class);
                    if (submission == null) {
                        System.out.println("Error al decodificar una submission desde JSON");
                        continue;
                    } else if (submission.getResult() == null) {
                        System.out.println("Submission no tiene result");
                        continue;
                    }
                    Result result = submission.getResult();

                    result = resultDAO.save(result);
                    submission.setResult(result);
                    activitySubmissionDAO.save(submission);

                    qs.confirmReceive();
                } catch (JsonGenerationException | JsonMappingException e) {
                    System.out.println("Error al decodificar Json como ActivitySubmission");
                    System.out.println(submissionString);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
