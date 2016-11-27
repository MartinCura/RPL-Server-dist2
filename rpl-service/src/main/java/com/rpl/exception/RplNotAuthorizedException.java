package com.rpl.exception;

public class RplNotAuthorizedException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RplNotAuthorizedException() {
		super();
	}
	
	public RplNotAuthorizedException(Exception e) {
		super(e);
	}

}
