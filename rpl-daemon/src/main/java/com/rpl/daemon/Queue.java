/*package com.rpl.daemon;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Queue {
	private String EXCHANGE_NAME = "daemon";
	private String queueName; 
	
	private QueueingConsumer consumer;
	private Connection connection;
	private Channel channel;

	public Queue() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("rpl");
        factory.setPassword("rpl");
        
        try {
	        connection = factory.newConnection();
	        channel = connection.createChannel();
	        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
	        queueName = channel.queueDeclare().getQueue();
	        consumer = new QueueingConsumer(channel);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	public void send(String message, String type) throws IOException {
		channel.basicPublish(EXCHANGE_NAME, type, null, message.getBytes());
		System.out.println("[Send] message: " + message + " - type: " + type);
	}
	
	public String receive(String type) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException {
		channel.queueBind(queueName, EXCHANGE_NAME, type);
		channel.basicConsume(queueName, true, consumer);
		
	    QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	    channel.queueUnbind(queueName, EXCHANGE_NAME, type);
	    
	    String message = new String(delivery.getBody());
	    System.out.println("[Receive] message: " + message + " - type: " + type);
	    return message;
	}
	
	public void close() throws IOException, TimeoutException {
		channel.close();
		connection.close();			

	}
}
*/