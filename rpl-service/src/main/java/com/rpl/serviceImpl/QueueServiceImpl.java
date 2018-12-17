package com.rpl.serviceImpl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;
import com.rpl.service.QueueService;

public class QueueServiceImpl implements QueueService {

    private static final String CONFIG_FILENAME = "rpl.config";
    private static final String QUEUE_HOST_ENV_VAR = "RPL_MASTER_HOST";
    private static final String QUEUE_HOST = "localhost";
    private static final String QUEUE_USER = "rpl";
    private static final String QUEUE_PASSWD = "rpl";
    private static final String SUBM_QUEUE_NAME = "rpl-subm";

    String queue_name = SUBM_QUEUE_NAME;
    private Connection connection;
    Channel channel;

    QueueServiceImpl() throws IOException, TimeoutException {
        connection = createConnection();
        channel = createChannel(connection);
    }

    QueueServiceImpl(String queue_name) throws IOException, TimeoutException {
        this.queue_name = queue_name;
        connection = createConnection();
        channel = createChannel(connection);
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

    private void close() throws IOException, TimeoutException {
        this.channel.close();
        this.connection.close();
    }

}
