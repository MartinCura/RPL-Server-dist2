package com.rpl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ActivitySubmission {
	@Id
	private Long id;
	@Column(name="submission_date")
	private Date submissionDate;
	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;
	private String code;
	private Status status;
	@Column(name="execution_output")
	private String executionOutput;
	
	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExecutionOutput() {
		return executionOutput;
	}

	public void setExecutionOutput(String executionOutput) {
		this.executionOutput = executionOutput;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
