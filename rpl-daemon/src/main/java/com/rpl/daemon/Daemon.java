package com.rpl.daemon;

import com.rpl.service.ExchangeService;
import com.rpl.service.QueueService;
import com.rpl.serviceImpl.ExchangeServiceImpl;
import com.rpl.serviceImpl.QueueServiceImpl;

public class Daemon {
	
    public static void main( String[] args ){
        
    	ExchangeService es = new ExchangeServiceImpl();
    	QueueService qs = new QueueServiceImpl();
    	
    	Producer producer = new Producer(es, qs);
    	Consumer consumer = new Consumer(es, qs);
    	
    	producer.start();
    	consumer.start();
    }
}
