package com.rpl.POJO;


import com.rpl.model.Course;
import com.rpl.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class CoursePOJO {
    private Long id;
    private String name;
    private List<TopicPOJO> topics;

    public CoursePOJO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        topics = new ArrayList<TopicPOJO>();
        for (Topic topic : course.getTopics()) {
            topics.add(new TopicPOJO(topic));
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TopicPOJO> getTopics() {
        return topics;
    }
}
