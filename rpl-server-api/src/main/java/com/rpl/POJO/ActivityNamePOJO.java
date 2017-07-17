package com.rpl.POJO;

import com.rpl.model.Activity;

public class ActivityNamePOJO {
    private Long id;
    private String name;

    public ActivityNamePOJO(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
