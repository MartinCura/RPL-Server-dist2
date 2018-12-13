package com.rpl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activity_file")
public class ActivityInputFile {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	///JsonIgnore
	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;
	private String fileName;
	private byte[] content;
	
	public ActivityInputFile() {
	}
	
	public ActivityInputFile(String fileName, byte[] content) {
		this.setFileName(fileName);
		this.setContent(content);
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

}
