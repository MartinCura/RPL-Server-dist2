package com.rpl.service;

import com.rpl.model.QueueMessage;

import java.io.IOException;

public interface QueueProducerService extends QueueService {

    void send(QueueMessage m) throws IOException;

}
