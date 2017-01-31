package com.rpl.persistence;

import java.util.List;

import com.rpl.model.Activity;
import com.rpl.model.DatabaseState;
import com.rpl.model.Language;

public class ActivityDAO extends ApplicationDAO {

	public Activity find(long id) {
		return entityManager.find(Activity.class, id);
	}

	public List<Activity> findByCourse(Long courseId) {
		return entityManager
				.createQuery("SELECT a FROM Activity a, Topic t WHERE a.state = :state AND a.topic.id = t.id AND t.course.id = :id")
				.setParameter("state", DatabaseState.ENABLED)
				.setParameter("id", courseId).getResultList();
	}

	public List<Activity> findByTopic(Long topicId) {
		return entityManager.createQuery("SELECT a FROM Activity a WHERE a.topic.id = :id AND a.state = :state").setParameter("id", topicId)
				.setParameter("state", DatabaseState.ENABLED).getResultList();
	}

	public void update(Long id, Language lang, int points, String name, String description, String template) {
		entityManager
				.createQuery(
						"UPDATE Activity set language = :lang , points = :points , name = :name , description = :description , template = :template "
								+ "where id = :id")
				.setParameter("id", id).setParameter("lang", lang).setParameter("points", points)
				.setParameter("name", name).setParameter("description", description).setParameter("template", template)
				.executeUpdate();
	}

	public void delete(Long id) {
		entityManager.createQuery("UPDATE Activity set state = :state where id = :id").setParameter("id", id)
				.setParameter("state", DatabaseState.DELETED).executeUpdate();
	}
}
