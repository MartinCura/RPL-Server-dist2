package com.rpl.service;

import java.io.IOException;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rpl.model.QueueMessage;

public interface QueueService {

    void send(QueueMessage m) throws IOException;

    QueueMessage receive() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException;

}
