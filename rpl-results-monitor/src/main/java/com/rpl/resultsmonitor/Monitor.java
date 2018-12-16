package com.rpl.resultsmonitor;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.QueueMessage;
import com.rpl.model.runner.Result;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.persistence.ApplicationDAO;
import com.rpl.persistence.ResultDAO;
import com.rpl.service.QueueConsumerService;
import com.rpl.service.util.JsonUtils;
import com.rpl.serviceImpl.QueueConsumerServiceImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Result monitor worker: persists the result for a submission, received by the specified RMQ queue.
 **/
public class Monitor {
    public static void main(String[] args) {
        ApplicationDAO.setBeanTransactionManagement();

        boolean running = true;
        while (running) {
            try {
                QueueConsumerService qs;
                try {
                    qs = new QueueConsumerServiceImpl("rpl-res");
                } catch (TimeoutException | IOException e) {
                    e.printStackTrace();
                    return;
                }
                ActivitySubmissionDAO activitySubmissionDAO = new ActivitySubmissionDAO();
                ResultDAO resultDAO = new ResultDAO();

                while (running) {
                    QueueMessage message = qs.receive();
                    String resultString = message.getMsg();
                    activitySubmissionDAO.clear();

                    try {
                        Result result = JsonUtils.jsonToObject(resultString, Result.class);
                        if (result == null) {
                            System.out.println("Error al decodificar un Result desde JSON");
                            continue;
                        }
                        ActivitySubmission submission = activitySubmissionDAO.find(result.getId());
                        // ToDo: check?

                        result = resultDAO.save(result);

                        submission.setResult(result);
                        submission.analyzeResult();
                        activitySubmissionDAO.save(submission);

                        qs.confirmReceive();
                    } catch (JsonGenerationException | JsonMappingException e) {
                        System.out.println("Error al decodificar Json como Result");
                        System.out.println("resultString:");
                        System.out.println(resultString);
                        e.printStackTrace();
                    }
                }
            } catch (ShutdownSignalException e) {
                System.out.println("Canal fue cerrado, me reinicio");
            } catch (Exception e) {
                System.err.println("ERROR INESPERADO");
                e.printStackTrace();
            }
        }

    }
}
