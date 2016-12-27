package com.rpl.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

	@Id
	private int id;

	private String name;
	private String mail;

	@Embedded
	private Credentials credentials;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "course_person",
			joinColumns = @JoinColumn(name = "person_id"),
			inverseJoinColumns = @JoinColumn(name = "course_id")
	)
	private List<Course> courses;
	// private List<ActivitySubmission> submissions;
	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	// public List<ActivitySubmission> getSubmissions() {
	// return submissions;
	// }
	// public void setSubmissions(List<ActivitySubmission> submissions) {
	// this.submissions = submissions;
	// }

}
