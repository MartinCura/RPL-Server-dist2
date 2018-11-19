package com.rpl.service;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rpl.model.QueueMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface QueueConsumerService {
	
	public void send(QueueMessage m) throws IOException, TimeoutException;
	
	public QueueMessage receive() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException;

	public void confirmReceive() throws IOException;

}
