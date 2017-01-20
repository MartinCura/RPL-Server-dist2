package com.rpl.service;

import com.rpl.model.Topic;

import java.util.List;

public interface TopicService {

    public Topic getTopicById(Long id);
    public List<Topic> getTopicsByCourse(Long courseId);
    public Topic submit(Long courseId, Topic topic);
	public void deleteTopicById(Long id);
}
