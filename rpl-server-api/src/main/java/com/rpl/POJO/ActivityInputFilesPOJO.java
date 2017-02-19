package com.rpl.POJO;

import java.util.List;

import com.rpl.model.ActivityInputFile;

public class ActivityInputFilesPOJO {
    private List<ActivityInputFile> files;

    public ActivityInputFilesPOJO(List<ActivityInputFile> files) {
    	this.setFiles(files);
    }

	public List<ActivityInputFile> getFiles() {
		return files;
	}

	public void setFiles(List<ActivityInputFile> files) {
		this.files = files;
	}
}
