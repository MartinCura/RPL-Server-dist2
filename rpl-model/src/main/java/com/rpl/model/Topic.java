package com.rpl.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "topic")
public class Topic {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="topic")
	private List<Activity> activities;
	
	@Enumerated(EnumType.STRING)
	private DatabaseState state;

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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
}
