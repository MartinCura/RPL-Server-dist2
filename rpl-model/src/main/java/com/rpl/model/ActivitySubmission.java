package com.rpl.model;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rpl.model.runner.Result;
import com.rpl.model.runner.ResultStatus;

@Entity
@Table(name="activity_submission")
public class ActivitySubmission {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="submission_date")
	private Date submissionDate;	// ToDo BUG: seems like submissionDate is not stored appropiately
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;
	private String code;
	@Enumerated(EnumType.STRING)
	private Status status;
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Result result;
	private boolean selected;
	private boolean definitive;
	
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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isDefinitive() {
		return definitive;
	}

	public void setDefinitive(boolean definitive) {
		this.definitive = definitive;
	}

	public void analyzeResult() {
		try {
			if (this.result.getStatus().getResult().equals(ResultStatus.STATUS_OK)) {
				this.setStatus( isExpectedOutput(this.result) ? Status.SUCCESS : Status.FAILURE );
			} else {
				this.setStatus( this.result.getStatus().getStage().equals("build") ?
						Status.BUILDING_ERROR : Status.RUNTIME_ERROR );
			}
		} catch (Exception e) {	// ToDo: specify
			e.printStackTrace();
		}
	}

	private boolean isExpectedOutput(Result result) {
		if (this.getActivity().getTestType().equals(TestType.INPUT)) {
			return result.getStdout().trim().equals(this.getActivity().getOutput());
		} else {
			return result.getTests().isSuccess();
		}
	}
}
