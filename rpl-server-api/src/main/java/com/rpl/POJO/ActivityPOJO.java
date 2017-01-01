package com.rpl.POJO;

import com.rpl.model.Activity;

public class ActivityPOJO {

    private String name;
    private String language;
    private int points;
    private Long topic;

    public ActivityPOJO(Activity activity) {
        this.name = activity.getName();
        this.language = activity.getLanguage().toString();
        this.points = activity.getPoints();
        this.topic  = activity.getTopic().getId();
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

    public Long getTopic() {
        return topic;
    }
}
