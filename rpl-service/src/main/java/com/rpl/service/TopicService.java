package com.rpl.service;

import com.rpl.model.Topic;

import java.util.List;

public interface TopicService {

    Topic getTopicById(Long id);
    List<Topic> getTopicsByCourse(Long courseId);
    List<Topic> getEnabledAndDisabledTopicsByCourse(Long courseId);
    Topic submit(Long courseId, Topic topic);
	void deleteTopicById(Long id);
	void update(Topic updateTopic);
	List<Topic> getTopicsByCourseEnabledAndDisabled(Long courseId);
	void hide(Long topicId);
	void unhide(Long topicId);

}
