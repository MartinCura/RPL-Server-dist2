package com.rpl.serviceImpl;

import com.rpl.model.Course;
import com.rpl.model.Topic;
import com.rpl.persistence.CourseDAO;
import com.rpl.persistence.TopicDAO;
import com.rpl.service.TopicService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

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
        return topicDAO.findByCourseId(courseId);
    }

    public void submit(Long courseId, Topic topic) {
        Course course = courseDAO.find(courseId);
        topic.setCourse(course);
        topicDAO.save(topic);
    }
}
