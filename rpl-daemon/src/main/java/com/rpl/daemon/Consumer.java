package com.rpl.daemon;

import com.rpl.model.QueueMessage;
import com.rpl.service.ExchangeService;
import com.rpl.service.QueueService;

public class Consumer extends Thread {

	private ExchangeService exchange;
	private QueueService queue;

	public Consumer(ExchangeService exchange, QueueService queue) {
		this.exchange = exchange;
		this.queue = queue;
	}

	public void run() {
		try {
			QueueMessage message1 = exchange.receive("1");
			QueueMessage message2 = queue.receive();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
