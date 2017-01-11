package com.rpl.service;

import com.rpl.model.Topic;

import java.util.List;

public interface TopicService {

    public Topic getTopicById(Long id);
    public List<Topic> getTopicsByCourse(Long courseId);
}
