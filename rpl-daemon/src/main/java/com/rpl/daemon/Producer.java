package com.rpl.daemon;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rpl.model.QueueMessage;
import com.rpl.service.QueueService;

public class Producer extends Thread {
	
	private QueueService queue;
	
	public Producer(QueueService queue) {
		this.queue = queue;
	}
	
    public void run() {
    	try {
    		queue.send(new QueueMessage("test"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
    }
}
