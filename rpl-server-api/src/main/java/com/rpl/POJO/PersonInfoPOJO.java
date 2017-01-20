package com.rpl.POJO;

import com.rpl.model.Person;

public class PersonInfoPOJO {

	private String name;
	private String mail;
	
	public PersonInfoPOJO(Person p) {
		this.name = p.getName();
		this.mail = p.getMail();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
