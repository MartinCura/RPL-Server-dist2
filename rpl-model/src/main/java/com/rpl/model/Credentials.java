package com.rpl.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Credentials {
	
	private String username;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	public Credentials() {
	}
	
	public Credentials(String user, String passwd, Role role) {
		this.username = user;
		this.password = passwd;
		this.role = role;
	}
	
	public Credentials(Map<String, Object> map) {
		this.setUsername((String)map.get("username"));
		this.setPassword((String)map.get("password"));
		this.setRole((Role)map.get("role"));
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Map<String, Object> toMap(){
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("username", this.username);
		map.put("password", this.password);
		map.put("role", this.role);
		return map;
	}

}
