package com.rpl.resultsmonitor;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

public class Monitor {
    public static void main(String[] args) {
        ApplicationDAO.setBeanTransactionManagement();

        QueueConsumerService qs;
        try {
            qs = new QueueConsumerServiceImpl("rpl-res");
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
//                String submissionString = message.getMsg();
                String resultString = message.getMsg();
                activitySubmissionDAO.clear();

                System.out.println("resultString:");//
                System.out.println(resultString);//
                System.out.println("----------------");//

                try {
//                    ActivitySubmission submission = JsonUtils.jsonToObject(submissionString, ActivitySubmission.class);
//                    Result result = submission.getResult();
                    Result result = JsonUtils.jsonToObject(resultString, Result.class);
                    if (result == null) {
                        System.out.println("Error al decodificar un Result desde JSON");
                        continue;
                    }
                    ActivitySubmission submission = activitySubmissionDAO.find(result.getId());
                    // ToDo: check?

                    ///
                    System.out.println("submissionString:");//
                    System.out.println(JsonUtils.objectToJson(submission));//
                    System.out.println("----------------");//
                    ///
                    result = resultDAO.save(result);

                    submission.setResult(result);
                    submission.analyzeResult();
                    activitySubmissionDAO.save(submission);

                    qs.confirmReceive();
                } catch (JsonGenerationException | JsonMappingException e) {
                    System.out.println("Error al decodificar Json como Result");
                    System.out.println("resultString:");//
                    System.out.println(resultString);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
