package com.rpl.serviceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.rpl.model.QueueMessage;
import com.rpl.service.QueueService;

public class QueueServiceImpl implements QueueService {

	private static final String CONFIG_FILENAME = "rpl.config";
	private static final String QUEUE_HOST_ENV_VAR = "RPL_MASTER_HOST";
	private static final String QUEUE_HOST = "localhost";
	private static final String QUEUE_USER = "rpl";
	private static final String QUEUE_PASSWD = "rpl";
	private static final String SUBM_QUEUE_NAME = "rpl-subm";

	private Connection connection;
	private Channel channel;
	private String queue_name = SUBM_QUEUE_NAME;

	public QueueServiceImpl() throws IOException, TimeoutException {
		connection = createConnection();
		channel = createChannel(connection);
		channel.basicQos(1);
		//close(channel, connection);	// TODO: Cuándo cerrar?
	}

	public QueueServiceImpl(String queue_name) throws IOException, TimeoutException {
		this.queue_name = queue_name;
		connection = createConnection();
		channel = createChannel(connection);
	}

	private Connection createConnection() throws IOException, TimeoutException {
		ConnectionFactory factory = createFactory();
		Connection new_connection = factory.newConnection();
		return new_connection;
	}

	private Channel createChannel(Connection connection) throws IOException {
		Channel new_channel = connection.createChannel();
		// channel.exchangeDeclare(SUBM_QUEUE_NAME, "direct", true);
		new_channel.queueDeclare(this.queue_name, true, false, false, null);
		//channel.queueDeclare(SUBM_QUEUE_NAME, false, false, false, null);
		// channel.queueBind(SUBM_QUEUE_NAME, SUBM_QUEUE_NAME, routingKey);
		int prefetchCount = 1;
		new_channel.basicQos(prefetchCount);
		return new_channel;
	}

	private String getQueueHost() {
		String host = System.getenv(QUEUE_HOST_ENV_VAR);
		if (host == null) {
			host = QUEUE_HOST; // default
			Properties prop = new Properties();
			InputStream is;
			try {
				is = new FileInputStream(CONFIG_FILENAME);
				try {
					prop.load(is);
					host = prop.getProperty("master.host");
				} catch (IOException ex) {
					System.out.println("Error al leer archivo de configuración");
				}
			} catch (FileNotFoundException ex) {
				System.out.println("Archivo de configuración no encontrado");
			}
		}
		System.out.println(FileSystems.getDefault().getPath(CONFIG_FILENAME));//
		System.out.println("WILL CONNECT TO " + host);
		return host;
	}

	private ConnectionFactory createFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(getQueueHost());
		factory.setUsername(QUEUE_USER);
		factory.setPassword(QUEUE_PASSWD);
		return factory;
	}

	public void send(QueueMessage m) throws IOException, TimeoutException {
		byte[] jsonMsg = new ObjectMapper().writeValueAsBytes(m);
		channel.basicPublish("", this.queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, jsonMsg);
		System.out.println("[Send] message: " + m.getMsg());
	}

	public QueueMessage receive()
			throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException {
		QueueingConsumer.Delivery delivery = getDeliveryFromChannel(channel);

		QueueMessage queueMessage = new ObjectMapper().readValue(delivery.getBody(), QueueMessage.class);
		System.out.println("[Receive] message: " + queueMessage.getMsg());
		return queueMessage;
	}

	private QueueingConsumer.Delivery getDeliveryFromChannel(Channel channel)
			throws IOException, InterruptedException {
		QueueingConsumer consumer = new QueueingConsumer(channel);	// TODO: Podría no crearse cada vez
		channel.basicConsume(this.queue_name, true, consumer);	// TODO: change autoAck and ack manually
		QueueingConsumer.Delivery delivery = consumer.nextDelivery(); // Solo consume el primer delivery!
		return delivery;
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
