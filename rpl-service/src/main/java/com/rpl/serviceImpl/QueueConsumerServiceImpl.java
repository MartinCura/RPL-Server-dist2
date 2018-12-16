package com.rpl.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.rpl.model.QueueMessage;
import com.rpl.service.QueueConsumerService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class QueueConsumerServiceImpl implements QueueConsumerService {

    private static final String CONFIG_FILENAME = "rpl.config";
    private static final String QUEUE_HOST_ENV_VAR = "RPL_MASTER_HOST";
    private static final String QUEUE_HOST = "localhost";
    private static final String QUEUE_USER = "rpl";
    private static final String QUEUE_PASSWD = "rpl";
    private static final String SUBM_QUEUE_NAME = "rpl-subm";

    private Connection connection;
    private Channel channel;
    private String queue_name = SUBM_QUEUE_NAME;
    private QueueingConsumer consumer;
    private QueueingConsumer.Delivery lastDelivery;

    public QueueConsumerServiceImpl() throws IOException, TimeoutException {
        connection = createConnection();
        channel = createChannel(connection);
        consumer = new QueueingConsumer(channel);
        boolean autoAck = false;
        channel.basicConsume(this.queue_name, autoAck, consumer);
        // ToDo: Cuándo cerrar? [close(channel, connection);]
    }

    public QueueConsumerServiceImpl(String queue_name) throws IOException, TimeoutException {
        this.queue_name = queue_name;
        connection = createConnection();
        channel = createChannel(connection);
        consumer = new QueueingConsumer(channel);
        boolean autoAck = false;
        channel.basicConsume(this.queue_name, autoAck, consumer);
    }

    private Connection createConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = createFactory();
        return factory.newConnection();
    }

    private Channel createChannel(Connection connection) throws IOException {
        Channel new_channel = connection.createChannel();
        new_channel.queueDeclare(this.queue_name, true, false, false, null);
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
                    System.out.println("Read configuration from " + FileSystems.getDefault().getPath(CONFIG_FILENAME));
                } catch (IOException ex) {
                    System.out.println("Error al leer archivo de configuración");
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Archivo de configuración no encontrado");
            }
        }
        System.out.println("Queue will connect to host at " + host);
        return host;
    }

    private ConnectionFactory createFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(getQueueHost());
        factory.setUsername(QUEUE_USER);
        factory.setPassword(QUEUE_PASSWD);
        return factory;
    }

    /// NO SE USA
    public void send(QueueMessage m) throws IOException {
        byte[] jsonMsg = new ObjectMapper().writeValueAsBytes(m);
        channel.basicPublish("", this.queue_name, MessageProperties.PERSISTENT_TEXT_PLAIN, jsonMsg);
        System.out.println("[Send] message: " + m.getMsg());
    }
    ///

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

    private void close(Channel channel, Connection connection) throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

}