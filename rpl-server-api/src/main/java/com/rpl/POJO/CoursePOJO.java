package com.rpl.POJO;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.rpl.model.Course;
import com.rpl.model.DatabaseState;
import com.rpl.model.Topic;

public class CoursePOJO {
    private Long id;
    private String name;
    private List<TopicPOJO> topics;
    private String rules;
    private String inscripted;

    public CoursePOJO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.rules = course.getRules();
        this.topics = new ArrayList<TopicPOJO>();
        this.inscripted = "UNREGISTERED";
        for (Topic topic : course.getTopics()) {
            if (topic.getState().equals(DatabaseState.ENABLED)) {
                topics.add(new TopicPOJO(topic));
            }
        }
    }
    
    public static CoursePOJO mapWithoutTopics(Course course){
    	CoursePOJO pojo = new CoursePOJO(course);
    	pojo.cleanTopics();
    	return pojo;
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
    
    private void cleanTopics() {
    	this.topics = null;
    }

    public String getInscripted() {
        return inscripted;
    }

    public void setInscripted(boolean accepted) {
        this.inscripted = accepted ? "ACCEPTED" : "PENDING";
    }

    public void markActivitiesAsSelected(Set<Long> activitiesSelected) {
        for (TopicPOJO topic : topics) {
            for (ActivityPOJO activity : topic.getActivities()) {
                if (activitiesSelected.contains(activity.getId())) {
                    activity.setSuccess(true);
                }
            }
        }
    }
}
