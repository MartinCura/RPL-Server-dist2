package com.rpl.exception;

public class RplException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Integer code;
	private final String msg;
	private final Exception cause;

	private RplException(Integer status, String msg, Exception cause) {
		this.code = status;
		this.msg = msg;
		this.cause = cause;
	}

	public static RplException of(Integer status, String msg, Exception cause) {
		return new RplException(status, msg, cause);
	}
	
	public static RplException of(Integer status, String msg) {
		return new RplException(status, msg, null);
	}
	
	public static RplException of(String msg, Exception cause) {
		return new RplException(null, msg, cause);
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public Exception getE() {
		return cause;
	}

}
