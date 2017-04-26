package com.rpl.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "logged_action")
public class LoggedAction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	@Column(name="action_description")
	private String actionDescription;
	@Column(name="action_date")
	private LocalDateTime actionDate;
	
	public LoggedAction() {
	}
	
	public LoggedAction(Person person, String desc, LocalDateTime date) {
		this.person = person;
		this.actionDescription = desc;
		this.actionDate = date;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getActionDescription() {
		return actionDescription;
	}
	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}
	public LocalDateTime getActionDate() {
		return actionDate;
	}
	public void setActionDate(LocalDateTime actionDate) {
		this.actionDate = actionDate;
	}

	
}
