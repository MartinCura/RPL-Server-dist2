package com.rpl.persistence;

import com.rpl.model.reports.*;

import java.util.List;

public class ReportDAO extends ApplicationDAO {

	public List<Report1> getReport1ByCourse(Long personId, Long courseId) {
		return entityManager.createNativeQuery("SELECT t.name as \"topicName\", a.name as \"activityName\", count(a.*) as \"quantityOfSubmissions\", " +
				"(SELECT id FROM activity_submission WHERE person_id = :personId AND activity_id = a.id AND selected = 't') as \"submissionId\" " +
				"FROM activity_submission s, person p, topic t, activity a " +
				"WHERE p.id = s.person_id AND s.activity_id = a.id AND a.topic_id = t.id AND s.person_id = :personId AND t.course_id = :courseId " +
				"GROUP BY a.id, t.name ORDER BY a.id", "report1")
				.setParameter("courseId", courseId).setParameter("personId", personId)
				.getResultList();

	}

	public List<Report1> getReport1ByTopic(Long personId, Long topicId) {
		return entityManager.createNativeQuery("SELECT t.name as \"topicName\", a.name as \"activityName\", count(a.*) as \"quantityOfSubmissions\", " +
				"(SELECT id FROM activity_submission WHERE person_id = :personId AND activity_id = a.id AND selected = 't') as \"submissionId\" " +
				"FROM activity_submission s, person p, topic t, activity a " +
				"WHERE p.id = s.person_id AND s.activity_id = a.id AND a.topic_id = t.id AND s.person_id = :personId AND t.id = :topicId " +
				"GROUP BY a.id, t.name ORDER BY a.id", "report1")
				.setParameter("topicId", topicId).setParameter("personId", personId)
				.getResultList();

	}

	public List<Report2> getReport2(Long topicId) {
		return entityManager.createNativeQuery("SELECT sq.activity as \"activityName\", avg(sq.count) as average FROM " +
				"(SELECT a.name as activity, s.person_id as person, count(*) " +
				"FROM activity_submission s, activity a " +
				"WHERE s.activity_id = a.id AND a.topic_id = :topicId AND exists " +
				"(select 1 from activity_submission where selected = 't' and person_id = s.person_id and activity_id = s.activity_id) " +
				"GROUP BY s.person_id, a.id) sq " +
				"GROUP BY sq.activity ORDER BY sq.activity", "report2")
				.setParameter("topicId", topicId)
				.getResultList();

	}

	public List<Report5> getReport5(Long topicId) {
		return entityManager.createNativeQuery("SELECT p.id as \"studentId\", p.name as \"studentName\", " +
				"round(sum(a.points) / (SELECT SUM(points) FROM activity WHERE topic_id= :topicId)\\:\\:numeric, 2) as percentage " +
				"FROM activity_submission s, activity a, person p " +
				"WHERE s.activity_id = a.id AND s.person_id = p.id AND a.topic_id = :topicId AND s.selected = 't' " +
				"GROUP BY p.id ORDER BY percentage desc", "report5")
				.setParameter("topicId", topicId)
				.getResultList();
	}

	public List<Report6> getReport6(Long topicId, Long personId) {
		return entityManager.createNativeQuery("SELECT a.name as \"activityName\", a.points as points, count(s.*) as tries " +
				"FROM activity_submission s, activity a, topic t, person p " +
				"WHERE s.activity_id = a.id AND s.person_id = p.id AND a.topic_id = t.id AND a.topic_id = :topicId AND s.person_id = :personId " +
				"GROUP BY a.id ORDER BY a.points desc, a.id", "report6")
				.setParameter("topicId", topicId).setParameter("personId", personId)
				.getResultList();
	}

}
