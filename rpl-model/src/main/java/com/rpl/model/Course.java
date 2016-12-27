package com.rpl.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "course")
public class Course {

	@Id
	private Long id;
	private String name;
	//TODO diferenciar estudiantes y profesores
	@ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL, mappedBy = "courses")
	private List<Person> professors;
	@ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL, mappedBy = "courses")
	private List<Person> students;
	
	//private Customization customization;
	
	@OneToMany
	private List<Topic> topics;

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

	public List<Person> getProfessors() {
		return professors;
	}

	public void setProfessors(List<Person> professors) {
		this.professors = professors;
	}

	public List<Person> getStudents() {
		return students;
	}

	public void setStudents(List<Person> students) {
		this.students = students;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	
}
