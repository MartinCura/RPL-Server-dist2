package com.rpl.POJO;

import com.rpl.model.Person;

public class PersonInfoPOJO {

	private Long id;
	private String name;
	private String mail;
	private String role;
	private Long studentId;
	
	public PersonInfoPOJO() {
	}
	
	public PersonInfoPOJO(Person p) {
		this.id = p.getId();
		this.name = p.getName();
		this.mail = p.getMail();
		this.role = p.getCredentials().getRole().toString();
		this.studentId = p.getStudentId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
}
