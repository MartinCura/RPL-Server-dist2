package com.rpl.exception;

public class RplItemDoesNotBelongToPersonException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RplItemDoesNotBelongToPersonException() {
		super();
	}
	
	public RplItemDoesNotBelongToPersonException(Exception e) {
		super(e);
	}

}
