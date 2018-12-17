package com.rpl.serviceImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.rpl.model.QueueMessage;
import com.rpl.service.QueueProducerService;

public class QueueProducerServiceImpl extends QueueServiceImpl implements QueueProducerService {

    public QueueProducerServiceImpl() throws IOException, TimeoutException {
        super();
    }

    public QueueProducerServiceImpl(String queue_name) throws IOException, TimeoutException {
        super(queue_name);
    }

    public void send(QueueMessage m) throws IOException {
        byte[] jsonMsg = new ObjectMapper().writeValueAsBytes(m);
        channel.basicPublish("", this.queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, jsonMsg);
        System.out.println("[Send] message: " + m.getMsg());
    }

}
