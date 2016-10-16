package com.rpl.daemon;

public class Consumer extends Thread {
	private Queue queue;
	
	public Consumer(Queue queue) {
		this.queue = queue;
	}
	
    public void run() {
        try {
			String message = queue.receive("2");
			String message2 = queue.receive("2");
			queue.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
