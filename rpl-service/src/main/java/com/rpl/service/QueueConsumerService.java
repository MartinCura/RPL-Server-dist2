package com.rpl.service;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rpl.model.QueueMessage;

import java.io.IOException;

public interface QueueConsumerService {

    void send(QueueMessage m) throws IOException;

    QueueMessage receive() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException;

    void confirmReceive() throws IOException;

}
