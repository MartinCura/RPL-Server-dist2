package com.rpl.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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

	private String customization;

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
}
