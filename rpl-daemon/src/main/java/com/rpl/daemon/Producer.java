package com.rpl.daemon;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rpl.model.QueueMessage;
import com.rpl.service.ExchangeService;
import com.rpl.service.QueueService;

public class Producer extends Thread {
	
	private ExchangeService exchange;
	private QueueService queue;
	
	public Producer(ExchangeService exchange, QueueService queue) {
		this.exchange = exchange;
		this.queue = queue;
	}
	
    public void run() {
    	try {
    		queue.send(new QueueMessage("1", "test"));
    		exchange.send(new QueueMessage("2","test1"), "1");
    		exchange.send(new QueueMessage("2","test2"), "2");
    		exchange.send(new QueueMessage("2","test3"), "2");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
    }
}
