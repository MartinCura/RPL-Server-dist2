package com.rpl.model;

import java.util.Date;

public class ActivitySubmission {

	private Long id;
	private Date submissionDate;
	private Activity activity;
	private String code;
	private Status status;
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
