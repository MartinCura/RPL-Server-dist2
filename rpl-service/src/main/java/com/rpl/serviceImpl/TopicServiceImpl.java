package com.rpl.serviceImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.model.Course;
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
        return topicDAO.findByCourseId(courseId);
    }

    public Topic submit(Long courseId, Topic topic) {
        Course course = courseDAO.find(courseId);
        topic.setCourse(course);
        return topicDAO.save(topic);
    }

	public void deleteTopicById(Long id) {
		topicDAO.delete(id);
	}
}
