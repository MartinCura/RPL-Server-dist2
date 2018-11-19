package com.rpl.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.rpl.model.QueueMessage;
import com.rpl.service.QueueConsumerService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueConsumerServiceImpl implements QueueConsumerService {

	private static final String QUEUE_HOST = "localhost";	// TODO
	private static final String QUEUE_USER = "rpl";
	private static final String QUEUE_PASSWD = "rpl";
	private static final String SUBM_QUEUE_NAME = "rpl-subm";

	private Connection connection;
	private Channel channel;
	private QueueingConsumer consumer;
	private QueueingConsumer.Delivery lastDelivery;

	public QueueConsumerServiceImpl() throws IOException, TimeoutException {
		connection = createConnection();
		channel = createChannel(connection);
		///channel.basicQos(1);///
		consumer = new QueueingConsumer(channel);
		boolean autoAck = false;
		channel.basicConsume(SUBM_QUEUE_NAME, autoAck, consumer);
		//close(channel, connection);	// TODO: Cuándo cerrar?
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

	///
	public void send(QueueMessage m) throws IOException, TimeoutException {
		byte[] jsonMsg = new ObjectMapper().writeValueAsBytes(m);
		channel.basicPublish("", SUBM_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, jsonMsg);
		System.out.println("[Send] message: " + m.getMsg());
	}
	///

	public QueueMessage receive()
			throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException {
		QueueingConsumer.Delivery delivery = getDeliveryFromChannel(channel);

		QueueMessage queueMessage = new ObjectMapper().readValue(delivery.getBody(), QueueMessage.class);
		System.out.println("[Receive] message: " + queueMessage.getMsg());
		lastDelivery = delivery;
		return queueMessage;
	}

	private QueueingConsumer.Delivery getDeliveryFromChannel(Channel channel)
			throws IOException, InterruptedException {
		//QueueingConsumer consumer = new QueueingConsumer(channel);	// TODO: Podría no crearse cada vez
		//channel.basicConsume(SUBM_QUEUE_NAME, true, consumer);	// TODO: change autoAck and ack manually
		QueueingConsumer.Delivery delivery = consumer.nextDelivery(); // Solo consume el primer delivery!
		return delivery;
	}

	public void confirmReceive() throws IOException {
		if (lastDelivery != null) {
			channel.basicAck(lastDelivery.getEnvelope().getDeliveryTag(), false);
			lastDelivery = null;
		}
	}

	//private void close() throws IOException, TimeoutException {
	//	channel.close();
	//	connection.close();
	//}

	private void close(Channel channel, Connection connection) throws IOException, TimeoutException {
		channel.close();
		connection.close();
	}
}
