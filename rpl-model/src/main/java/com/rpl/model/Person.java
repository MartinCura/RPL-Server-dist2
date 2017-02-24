package com.rpl.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

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

	@OneToMany(fetch = FetchType.EAGER, mappedBy="person")
	private Set<ActivitySubmission> submissions;
	
	@OneToMany(mappedBy="person")
	private List<CoursePerson> coursePersons;

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

	public Set<ActivitySubmission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(Set<ActivitySubmission> submissions) {
		this.submissions = submissions;
	}

	public List<CoursePerson> getCoursePersons() {
		return coursePersons;
	}

	public void setCoursePersons(List<CoursePerson> coursePersons) {
		this.coursePersons = coursePersons;
	}

}
