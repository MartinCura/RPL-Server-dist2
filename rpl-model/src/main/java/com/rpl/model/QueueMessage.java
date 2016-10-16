package com.rpl.model;

public class QueueMessage {
	
	private String senderId;
	private String msg;
	
	public QueueMessage() {
		//Default ctor needed to seralize into JSON
	}
	
	public QueueMessage(String senderId, String msg) {
		this.senderId = senderId;
		this.msg = msg;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
