package com.rpl.POJO;

public class MessagePOJO {

	private final Integer code;
	private final String msg;

	private MessagePOJO(Integer status, String msg) {
		this.code = status;
		this.msg = msg;
	}

	public static MessagePOJO of(Integer status, String msg) {
		return new MessagePOJO(status, msg);
	}
	
	public static MessagePOJO of(String msg) {
		return new MessagePOJO(null, msg);
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
