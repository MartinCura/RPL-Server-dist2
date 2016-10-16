package com.rpl.serviceImpl;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rpl.model.QueueMessage;
import com.rpl.service.QueueService;

public class QueueServiceImpl implements QueueService {

	private static final String QUEUE_HOST = "localhost";
	private static final String QUEUE_USER = "rpl";
	private static final String QUEUE_PASSWD = "rpl";
	private static final String CHANNEL_TYPE = "topic";
	private static final String EXCHANGE_NAME = "EXCHANGE";

	public QueueServiceImpl() {
	}

	private Connection createConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = createFactory();
		Connection connection = factory.newConnection();
		return connection;
	}

	private Channel createChannel(Connection connection) throws IOException {
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, CHANNEL_TYPE);
		return channel;
	}

	private ConnectionFactory createFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(QUEUE_HOST);
		factory.setUsername(QUEUE_USER);
		factory.setPassword(QUEUE_PASSWD);
		return factory;
	}

	public void send(QueueMessage m, String type) throws IOException, TimeoutException {
		Connection connection = createConnection();
		Channel channel = createChannel(connection);
		byte[] jsonMsg = new ObjectMapper().writeValueAsBytes(m);
		channel.basicPublish(EXCHANGE_NAME, type, null, jsonMsg);
		System.out.println("[Send] message: " + m.getMsg() + " - type: " + type);
		close(channel, connection);
	}

	public QueueMessage receive(String type)
			throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException {
		Connection connection = createConnection();
		Channel channel = createChannel(connection);
		QueueingConsumer.Delivery delivery = getDeliveryFromChannel(channel, type);

		QueueMessage queueMessage = new ObjectMapper().readValue(delivery.getBody(), QueueMessage.class);
		System.out.println("[Receive] message: " + queueMessage.getMsg() + " - type: " + type);
		close(channel, connection);
		return queueMessage;
	}

	private QueueingConsumer.Delivery getDeliveryFromChannel(Channel channel, String type)
			throws IOException, InterruptedException {
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, type);
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(queueName, true, consumer);
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		channel.queueUnbind(queueName, EXCHANGE_NAME, type);
		return delivery;
	}

	private void close(Channel channel, Connection connection) throws IOException, TimeoutException {
		channel.close();
		connection.close();
	}
}
