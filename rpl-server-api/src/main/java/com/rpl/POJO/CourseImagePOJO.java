package com.rpl.POJO;

import com.rpl.model.CourseImage;

public class CourseImagePOJO {
	private Long id;
	private String fileName;
	private byte[] content;
	
	public CourseImagePOJO() {
	}
	
	public CourseImagePOJO(CourseImage p) {
		this.setFileName(p.getFileName());
		this.setContent(p.getContent());
		this.setId(p.getId());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
