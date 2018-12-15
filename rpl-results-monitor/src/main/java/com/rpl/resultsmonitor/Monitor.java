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

        /*try {
            System.out.println("1");//
            java.util.concurrent.TimeUnit.SECONDS.sleep(5);//
        } catch (InterruptedException e) {
            e.printStackTrace();//
        }*/

        ActivitySubmissionDAO activitySubmissionDAO = new ActivitySubmissionDAO();
        ResultDAO resultDAO = new ResultDAO();
        boolean running = true;

        while (running) {
            try {
                /*try {
                    System.out.println("2");//
                    java.util.concurrent.TimeUnit.SECONDS.sleep(5);//
                } catch (InterruptedException e) {
                    e.printStackTrace();//
                }*/

                QueueMessage message = qs.receive();
                String submissionString = message.getMsg();
                System.out.println("submissionString:");//
                System.out.println(submissionString);//
                System.out.println("----------------");//
                /*try {
                    System.out.println("3");//
                    java.util.concurrent.TimeUnit.SECONDS.sleep(5);//
                } catch (InterruptedException e) {
                    e.printStackTrace();//
                }*/
                activitySubmissionDAO.clear();

                try {
                    ActivitySubmission submission = JsonUtils.jsonToObject(submissionString, ActivitySubmission.class);
                    //= activitySubmissionDAO.find(Long.valueOf(submissionId));//Alguna razón para agarrarlo?
                    if (submission == null) {
                        System.out.println("Error al decodificar una submission desde JSON");
                        continue;
                    } else if (submission.getResult() == null) {
                        System.out.println("Submission no tiene result");
                        continue;
                    }
                    ///
                    ActivitySubmission submission2 = activitySubmissionDAO.find(submission.getId());//
                    System.out.println("submission2:");//
                    System.out.println(JsonUtils.objectToJson(submission2));//
                    System.out.println("------------");//
                    ///
                    Result result = submission.getResult();

                    /*try {
                        System.out.println("4");//
                        //java.util.concurrent.TimeUnit.SECONDS.sleep(5);//
                    } catch (InterruptedException e) {
                        e.printStackTrace();//
                    }*/

                    System.out.println();
                    result = resultDAO.save(result);
                    submission.setResult(result);
                    activitySubmissionDAO.save(submission);

                    /*try {
                        System.out.println("5");//
                        //java.util.concurrent.TimeUnit.SECONDS.sleep(5);//
                    } catch (InterruptedException e) {
                        e.printStackTrace();//
                    }*/

                    qs.confirmReceive();
                } catch (JsonGenerationException | JsonMappingException e) {
                    System.out.println("Error al decodificar Json como ActivitySubmission");
                    System.out.println("submissionString:");//
                    System.out.println(submissionString);
                    e.printStackTrace();
                }
                /*try {
                    System.out.println("6");//
                    java.util.concurrent.TimeUnit.SECONDS.sleep(5);//
                } catch (InterruptedException e) {
                    e.printStackTrace();//
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
