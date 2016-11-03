package com.rpl.daemon;

import com.rpl.service.QueueService;
import com.rpl.serviceImpl.QueueServiceImpl;

public class Daemon {
	
    public static void main( String[] args ){
        
    	QueueService qs = new QueueServiceImpl();
    	
    	Producer producer = new Producer(qs);
    	Consumer consumer = new Consumer(qs);
    	
    	producer.start();
    	consumer.start();
    }
}
