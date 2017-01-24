package com.rpl.POJO;

import com.rpl.model.Role;

public class TokenPOJO {

	private final String token;
	private final Role role;

	private TokenPOJO(String token, Role rol){
		this.token = token;
		this.role = rol;
	}
	
	public static TokenPOJO of (String token, Role rol){
		return new TokenPOJO(token, rol);
	}
	
	public String getToken() {
		return token;
	}
	
	public Role getRol() {
		return role;
	}
}
