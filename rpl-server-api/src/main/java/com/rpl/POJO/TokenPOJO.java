package com.rpl.POJO;

public class TokenPOJO {

	private final String token;

	private TokenPOJO(String token){
		this.token = token;
	}
	
	public static TokenPOJO of (String token){
		return new TokenPOJO(token);
	}
	
	public String getToken() {
		return token;
	}
	
}
