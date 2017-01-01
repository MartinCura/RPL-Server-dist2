package com.rpl.POJO;


import com.rpl.model.Topic;

public class TopicPOJO {
    private String name;
    private Long course;

    public TopicPOJO(Topic topic) {
        this.name = topic.getName();
        this.course = topic.getCourse().getId();
    }

    public String getName() {
        return name;
    }

    public Long getCourse() {
        return course;
    }
}
