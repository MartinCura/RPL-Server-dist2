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

	private static final String QUEUE_HOST = "localhost";	// TODO
	private static final String QUEUE_USER = "rpl";
	private static final String QUEUE_PASSWD = "rpl";
	private static final String SUBM_QUEUE_NAME = "rpl";

	//private Connection connection;
	//private Channel channel;

	public QueueServiceImpl() throws IOException, TimeoutException {
		///System.out.println("CREO COLA AMIGUITOOOOOOOOOOOOOOOOOOOOOOOOOO\n\n\n");//
		//this.connection = createConnection();
		//this.channel = createChannel(connection);
		Connection connection = createConnection();
		Channel channel = createChannel(connection);
		close(channel, connection);
	}

	private Connection createConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = createFactory();
		Connection new_connection = factory.newConnection();
		return new_connection;
	}

	private Channel createChannel(Connection connection) throws IOException {
		Channel new_channel = connection.createChannel();
		// channel.exchangeDeclare(SUBM_QUEUE_NAME, "direct", true);
		new_channel.queueDeclare(SUBM_QUEUE_NAME, true, false, false, null);
		//channel.queueDeclare(SUBM_QUEUE_NAME, false, false, false, null);
		// channel.queueBind(SUBM_QUEUE_NAME, SUBM_QUEUE_NAME, routingKey);
		int prefetchCount = 1;
		new_channel.basicQos(prefetchCount);
		return new_channel;
	}

	private ConnectionFactory createFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(QUEUE_HOST);
		factory.setUsername(QUEUE_USER);
		factory.setPassword(QUEUE_PASSWD);
		return factory;
	}

	public void send(QueueMessage m) throws IOException, TimeoutException {
		Connection connection = createConnection();
		Channel channel = createChannel(connection);

		byte[] jsonMsg = new ObjectMapper().writeValueAsBytes(m);
		//this.channel.basicPublish("", SUBM_QUEUE_NAME, null, jsonMsg);
		channel.basicPublish("", SUBM_QUEUE_NAME, null, jsonMsg);
		System.out.println("[Send] message: " + m.getMsg());

		close(channel, connection);
	}

	public QueueMessage receive()
			throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException {
		Connection connection = createConnection();
		Channel channel = createChannel(connection);
		//QueueingConsumer.Delivery delivery = getDeliveryFromChannel(this.channel);
		QueueingConsumer.Delivery delivery = getDeliveryFromChannel(channel);

		QueueMessage queueMessage = new ObjectMapper().readValue(delivery.getBody(), QueueMessage.class);
		System.out.println("[Receive] message: " + queueMessage.getMsg());
		// close(channel, connection); TODO: CUÁNDO LLAMAR?
		return queueMessage;
	}

	private QueueingConsumer.Delivery getDeliveryFromChannel(Channel channel)
			throws IOException, InterruptedException {
		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(SUBM_QUEUE_NAME, true, consumer);
		QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		return delivery;
	}

	//private void close() throws IOException, TimeoutException {
	//	this.channel.close();
	//	this.connection.close();
	//}

	private void close(Channel channel, Connection connection) throws IOException, TimeoutException {
		channel.close();
		connection.close();
	}
}
