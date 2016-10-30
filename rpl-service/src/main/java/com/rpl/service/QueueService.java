package com.rpl.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rpl.model.QueueMessage;

public interface QueueService {
	
	public void send(QueueMessage m) throws IOException, TimeoutException;
	
	public QueueMessage receive() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException;

}
