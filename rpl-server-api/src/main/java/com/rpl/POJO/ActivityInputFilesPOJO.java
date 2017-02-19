package com.rpl.POJO;

import java.util.List;
import java.util.stream.Collectors;

import com.rpl.model.ActivityInputFile;

public class ActivityInputFilesPOJO {
    private List<ActivityInputFilePOJO> files;

    public ActivityInputFilesPOJO(List<ActivityInputFile> files) {
    	this.files = files.stream().map(f -> new ActivityInputFilePOJO(f)).collect(Collectors.toList());
    }

	public List<ActivityInputFilePOJO> getFiles() {
		return files;
	}

	public void setFiles(List<ActivityInputFilePOJO> files) {
		this.files = files;
	}
}
