package com.rpl.model.runner;

import javax.persistence.*;

@Entity
@Table(name = "result")
public class Result {

	@Id
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
	private ResultStatus status;

	private String stdout;

	@OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
	private Tests tests;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ResultStatus getStatus() {
		return status;
	}

	public void setStatus(ResultStatus status) {
		this.status = status;
	}

	public String getStdout() {
		return stdout;
	}

	public void setStdout(String stdout) {
		this.stdout = stdout;
	}

	public Tests getTests() {
		return tests;
	}

	public void setTests(Tests tests) {
		this.tests = tests;
	}

	public void setIds(Long id) {
		this.setId(id);
		if (status != null)
			status.setId(id);
		if (tests != null)
			tests.setId(id);
	}
}
