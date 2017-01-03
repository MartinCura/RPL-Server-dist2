package com.rpl.POJO;


import com.rpl.model.Activity;
import com.rpl.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicPOJO {
    private Long id;
    private String name;
    private List<ActivityPOJO> activities;

    public TopicPOJO(Topic topic) {
        this.id = topic.getId();
        this.name = topic.getName();
        activities = new ArrayList<ActivityPOJO>();
        for (Activity activity : topic.getActivities()) {
            activities.add(new ActivityPOJO(activity));
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ActivityPOJO> getActivities() {
        return activities;
    }
}
