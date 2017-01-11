package com.rpl.persistence;


import com.rpl.model.Topic;

import java.util.List;

public class TopicDAO extends ApplicationDAO {

    public Topic find(long id) {
        return entityManager.find(Topic.class, id);
    }

    public List<Topic> findByCourseId(Long courseId) {
        return entityManager.createQuery(
                "SELECT t FROM Topic t WHERE t.course.id = :courseId")
                .setParameter("courseId", courseId)
                .getResultList();
    }
}
