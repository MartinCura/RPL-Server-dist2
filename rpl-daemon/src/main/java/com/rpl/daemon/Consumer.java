package com.rpl.daemon;

import com.rpl.model.QueueMessage;
import com.rpl.service.QueueService;

public class Consumer extends Thread {

	private QueueService queue;

	public Consumer(QueueService queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			QueueMessage message2 = queue.receive();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
