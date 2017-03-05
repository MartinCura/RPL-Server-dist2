package com.rpl.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "course")
@DynamicInsert
@DynamicUpdate
public class Course {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;

	@Enumerated(EnumType.STRING)
	private DatabaseState state;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "course", orphanRemoval = true)
	@OrderBy(clause = "id ASC")
	private Set<Topic> topics;
	
	private String rules;

	private String description;

	private String customization;
	
	@OneToOne(optional = true, mappedBy = "course")
	private CourseImage courseImage;
	
	@OneToMany(mappedBy = "course", orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy(clause = "minScore ASC")
	private List<Range> ranges;
	
	public String getCustomization() {
		return customization;
	}

	public void setCustomization(String customization) {
		this.customization = customization;
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

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}
	
	public DatabaseState getState() {
		return state;
	}

	public void setState(DatabaseState state) {
		this.state = state;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public CourseImage getCourseImage() {
		return courseImage;
	}

	public void setCourseImage(CourseImage courseImage) {
		this.courseImage = courseImage;
	}

	public List<Range> getRanges() {
		return ranges;
	}

	public void setRanges(List<Range> ranges) {
		this.ranges = ranges;
	}
}
