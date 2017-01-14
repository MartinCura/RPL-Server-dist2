package com.rpl.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	//TODO diferenciar estudiantes y profesores
	@ManyToMany(cascade= CascadeType.ALL, mappedBy = "courses")
	private List<Person> professors;
	@ManyToMany(cascade= CascadeType.ALL, mappedBy = "courses")
	private List<Person> students;
	
	//private Customization customization;
	
	@OneToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "course", orphanRemoval = true)
	private Set<Topic> topics;

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

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	
}
