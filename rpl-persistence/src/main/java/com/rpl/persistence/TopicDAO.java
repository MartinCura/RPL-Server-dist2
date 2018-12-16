package com.rpl.persistence;

import com.rpl.model.DatabaseState;
import com.rpl.model.Topic;

import java.util.List;

public class TopicDAO extends ApplicationDAO {

    public Topic find(long id) {
        return entityManager.find(Topic.class, id);
    }

    public List<Topic> findByCourseIdEnabledOnly(Long courseId) {
        String q = "SELECT t FROM Topic t WHERE t.course.id = :courseId AND t.state = :state ORDER BY t.name";
        return entityManager
                .createQuery(q, Topic.class)
                .setParameter("courseId", courseId)
                .setParameter("state", DatabaseState.ENABLED)
                .getResultList();
    }

    public List<Topic> findByCourseIdEnabledAndDisabled(Long courseId) {
        String q = "SELECT t FROM Topic t WHERE (t.state = :enabledState OR t.state = :disabledState) AND t.course.id = :courseId ORDER BY t.name";
        return entityManager
                .createQuery(q, Topic.class)
                .setParameter("courseId", courseId)
                .setParameter("enabledState", DatabaseState.ENABLED)
                .setParameter("disabledState", DatabaseState.DISABLED)
                .getResultList();
    }

    public void delete(Long id) {
        updateDatabaseState(id, DatabaseState.DELETED);
    }

    public void update(Long id, String name) {
        entityManager
                .createQuery("UPDATE Topic set name = :name where id = :id")
                .setParameter("id", id)
                .setParameter("name", name)
                .executeUpdate();
    }

    public void updateDatabaseState(Long topicId, DatabaseState state) {
        entityManager
                .createQuery("UPDATE Topic set state = :state where id = :id")
                .setParameter("id", topicId)
                .setParameter("state", state)
                .executeUpdate();
    }

}
