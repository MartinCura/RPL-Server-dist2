package com.rpl.daemon;

import java.io.IOException;

public class Producer extends Thread {
	private Queue queue;
	
	public Producer(Queue queue) {
		this.queue = queue;
	}
	
    public void run() {
    	try {
    		queue.send("test1", "1");
			queue.send("test2", "2");
			queue.send("test3", "2");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
