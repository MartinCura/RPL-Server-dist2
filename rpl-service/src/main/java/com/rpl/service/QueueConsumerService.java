package com.rpl.service;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rpl.model.QueueMessage;
import com.rpl.serviceImpl.QueueServiceImpl;

import java.io.IOException;

public interface QueueConsumerService extends QueueService {

    QueueMessage receive() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException;

    void confirmReceive() throws IOException;

}
