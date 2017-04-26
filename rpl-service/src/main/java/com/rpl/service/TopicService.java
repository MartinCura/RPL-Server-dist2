package com.rpl.service;

import com.rpl.model.Topic;

import java.util.List;

public interface TopicService {

    public Topic getTopicById(Long id);
    public List<Topic> getTopicsByCourse(Long courseId);
    public List<Topic> getEnabledAndDisabledTopicsByCourse(Long courseId);
    public Topic submit(Long courseId, Topic topic);
	public void deleteTopicById(Long id);
	public void update(Topic updateTopic);
	public List<Topic> getTopicsByCourseEnabledAndDisabled(Long courseId);
	public void hide(Long topicId);
	public void unhide(Long topicId);
}
