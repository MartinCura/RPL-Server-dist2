package com.rpl.POJO;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.rpl.model.Course;
import com.rpl.model.Topic;

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
    
    public CoursePOJO(Set<Topic> topics) {
    	this.topics = new ArrayList<TopicPOJO>();
    	for (Topic topic : topics) {
            this.topics.add(new TopicPOJO(topic));
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
