package com.rpl.persistence;

import com.rpl.model.Activity;

import java.util.List;

public class ActivityDAO extends ApplicationDAO {

	public Activity find(long id) {
		return entityManager.find(Activity.class, id);
	}
	
	public Activity save(Activity act){
		return this.merge(act);
	}

    public List<Activity> findByCourse(Long courseId) {
		return entityManager.createQuery("SELECT a FROM Activity a, Topic t WHERE a.topic.id = t.id AND t.course.id = :id").setParameter("id", courseId).getResultList();
    }

	public List<Activity> findByTopic(Long topicId) {
		return entityManager.createQuery("SELECT a FROM Activity a WHERE a.topic.id = :id").setParameter("id", topicId).getResultList();
	}
}
