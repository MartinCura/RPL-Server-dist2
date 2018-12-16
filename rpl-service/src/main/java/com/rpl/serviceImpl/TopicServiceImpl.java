package com.rpl.serviceImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.model.Course;
import com.rpl.model.DatabaseState;
import com.rpl.model.Topic;
import com.rpl.persistence.CourseDAO;
import com.rpl.persistence.TopicDAO;
import com.rpl.service.TopicService;

@Stateless
public class TopicServiceImpl implements TopicService{
    @Inject
    private TopicDAO topicDAO;
    @Inject
    private CourseDAO courseDAO;

    public Topic getTopicById(Long id) {
        return topicDAO.find(id);
    }

    public List<Topic> getTopicsByCourse(Long courseId) {
        return topicDAO.findByCourseIdEnabledOnly(courseId);
    }
    
    public List<Topic> getTopicsByCourseEnabledAndDisabled(Long courseId) {
        return topicDAO.findByCourseIdEnabledAndDisabled(courseId);
    }

    public Topic submit(Long courseId, Topic topic) {
        Course course = courseDAO.find(courseId);
        topic.setCourse(course);
        return topicDAO.save(topic);
    }

    public void deleteTopicById(Long id) {
        topicDAO.delete(id);
    }

    public void update(Topic updateTopic){
        topicDAO.update(updateTopic.getId(), updateTopic.getName());
    }

    @Override
    public List<Topic> getEnabledAndDisabledTopicsByCourse(Long courseId) {
        return topicDAO.findByCourseIdEnabledAndDisabled(courseId);
    }

    @Override
    public void hide(Long topicId) {
        topicDAO.updateDatabaseState(topicId, DatabaseState.DISABLED);
    }

    @Override
    public void unhide(Long topicId) {
        topicDAO.updateDatabaseState(topicId, DatabaseState.ENABLED);

    }
}
