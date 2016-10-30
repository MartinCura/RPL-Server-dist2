package com.rpl.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rpl.model.QueueMessage;

public interface ExchangeService {
	
	public void send(QueueMessage m, String receiverId) throws IOException, TimeoutException;
	
	public QueueMessage receive(String type) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException;

}
