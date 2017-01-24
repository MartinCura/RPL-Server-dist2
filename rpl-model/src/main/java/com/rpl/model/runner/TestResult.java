package com.rpl.model.runner;

import javax.persistence.*;

@Entity
@Table(name = "test_result")
public class TestResult {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private boolean success;
	private String description;
	@ManyToOne
	@JoinColumn(name = "test_id")
	private Tests test;

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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Tests getTest() {
		return test;
	}

	public void setTest(Tests test) {
		this.test = test;
	}
}
