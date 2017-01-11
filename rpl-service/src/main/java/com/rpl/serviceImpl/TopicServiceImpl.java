package com.rpl.serviceImpl;

import com.rpl.model.Topic;
import com.rpl.persistence.TopicDAO;
import com.rpl.service.TopicService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TopicServiceImpl implements TopicService{
    @Inject
    private TopicDAO topicDAO;

    public Topic getTopicById(Long id) {
        return topicDAO.find(id);
    }

    public List<Topic> getTopicsByCourse(Long courseId) {
        return topicDAO.findByCourseId(courseId);
    }
}
