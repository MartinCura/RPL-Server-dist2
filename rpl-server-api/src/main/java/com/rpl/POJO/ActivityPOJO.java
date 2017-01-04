package com.rpl.POJO;

import com.rpl.model.Activity;

public class ActivityPOJO {
    private Long id;
    private String name;
    private String language;
    private String description;
    private String template;
    private int points;

    public ActivityPOJO(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.language = activity.getLanguage().toString();
        this.description = activity.getDescription();
        this.template = activity.getTemplate();
        this.points = activity.getPoints();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public int getPoints() {
        return points;
    }


    public String getDescription() {
        return description;
    }

    public String getTemplate() {
        return template;
    }
}
