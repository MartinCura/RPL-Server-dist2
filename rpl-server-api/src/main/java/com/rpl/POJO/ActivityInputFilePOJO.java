package com.rpl.POJO;

import com.rpl.model.ActivityInputFile;

public class ActivityInputFilePOJO {

    private Long id;
    private String name;

    public ActivityInputFilePOJO(ActivityInputFile file) {
        this.setId(file.getId());
        this.setName(file.getFileName());
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


}
