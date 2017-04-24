package com.rpl.persistence;

import java.util.List;

import javax.persistence.NoResultException;

import com.rpl.model.Activity;
import com.rpl.model.ActivityInputFile;
import com.rpl.model.DatabaseState;
import com.rpl.model.Language;
import com.rpl.model.TestType;

public class ActivityDAO extends ApplicationDAO {

	public Activity find(long id) {
		return entityManager.find(Activity.class, id);
	}

	public List<Activity> findByCourseEnabledOnly(Long courseId) {
		return entityManager
				.createQuery(
						"SELECT a FROM Activity a, Topic t WHERE a.state = :state AND a.topic.id = t.id AND t.course.id = :id ORDER BY a.name")
				.setParameter("state", DatabaseState.ENABLED).setParameter("id", courseId).getResultList();
	}
	
	public List<Activity> findByCourseEnabledAndDisabled(Long courseId) {
		return entityManager
				.createQuery(
						"SELECT a FROM Activity a, Topic t WHERE (a.state = :enabledState OR a.state = :disabledState) AND a.topic.id = t.id AND t.course.id = :id ORDER BY a.name DESC")
				.setParameter("enabledState", DatabaseState.ENABLED)
				.setParameter("disabledState", DatabaseState.DISABLED).setParameter("id", courseId).getResultList();
	}

	public List<Activity> findByTopic(Long topicId) {
		return entityManager.createQuery("SELECT a FROM Activity a WHERE a.topic.id = :id AND a.state = :state ORDER BY a.name")
				.setParameter("id", topicId).setParameter("state", DatabaseState.ENABLED).getResultList();
	}

	public void update(Long id, String name, String description, Language language, int points, Long topic,
			TestType testType, String template, String input, String output, String tests) {
		entityManager
				.createQuery(
						"UPDATE Activity set name = :name, description = :description, language = :language, points = :points, topic.id = :topic, "
								+ "testType = :testType, template = :template, input = :input, output = :output, tests = :tests"
								+ " WHERE id = :id")
				.setParameter("id", id).setParameter("name", name).setParameter("description", description)
				.setParameter("language", language).setParameter("points", points).setParameter("topic", topic)
				.setParameter("testType", testType).setParameter("template", template).setParameter("input", input)
				.setParameter("output", output).setParameter("tests", tests).executeUpdate();
	}

	public void delete(Long id) {
		updateDatabaseState(id, DatabaseState.DELETED);
	}

	public ActivityInputFile findFile(Long fileId) {
		return entityManager.find(ActivityInputFile.class, fileId);
	}

	public ActivityInputFile findFileByActivityAndName(Long activityId, String fileName) {
		try{
			return (ActivityInputFile) entityManager
					.createQuery("SELECT file FROM ActivityInputFile file WHERE file.activity.id = :activityId AND file.fileName = :fileName")
					.setParameter("activityId", activityId).setParameter("fileName", fileName).getSingleResult();
		} catch (NoResultException e){
			return null;
		}
		
	}

	public void deleteFile(ActivityInputFile file) {
		entityManager.remove(file);
	}

	public List<ActivityInputFile> findFiles(Long activityId) {
		return entityManager.createQuery("SELECT file FROM ActivityInputFile file WHERE file.activity.id = :id")
				.setParameter("id", activityId).getResultList();
	}

	public void updateDatabaseState(Long activityId, DatabaseState state) {
		entityManager.createQuery("UPDATE Activity set state = :state where id = :id").setParameter("id", activityId)
		.setParameter("state", state).executeUpdate();
		
	}
}
