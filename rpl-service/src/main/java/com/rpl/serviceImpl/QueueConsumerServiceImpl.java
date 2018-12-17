package com.rpl.serviceImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.rpl.model.QueueMessage;
import com.rpl.service.QueueConsumerService;

public class QueueConsumerServiceImpl extends QueueServiceImpl implements QueueConsumerService {

    private QueueingConsumer consumer;
    private QueueingConsumer.Delivery lastDelivery;

    public QueueConsumerServiceImpl(String queue_name) throws IOException, TimeoutException {
        super(queue_name);
        consumer = createConsumer();
    }

    private QueueingConsumer createConsumer() throws IOException {
        consumer = new QueueingConsumer(channel);
        boolean autoAck = false;
        this.channel.basicConsume(this.queue_name, autoAck, consumer);
        return consumer;
    }

    public QueueMessage receive()
            throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
        QueueingConsumer.Delivery delivery = getDeliveryFromChannel(channel);

        QueueMessage queueMessage = new ObjectMapper().readValue(delivery.getBody(), QueueMessage.class);
        System.out.println("[Receive] message: " + queueMessage.getMsg());
        lastDelivery = delivery;
        return queueMessage;
    }

    private QueueingConsumer.Delivery getDeliveryFromChannel(Channel channel)
            throws InterruptedException {
        return consumer.nextDelivery();
    }

    public void confirmReceive() throws IOException {
        if (lastDelivery != null) {
            channel.basicAck(lastDelivery.getEnvelope().getDeliveryTag(), false);
            lastDelivery = null;
        }
    }

}
