package com.rpl.persistence;

import com.rpl.model.reports.Report1;

import java.util.List;

public class ReportDAO extends ApplicationDAO {

	public List<Report1> getReport1(Long personId) {
		return entityManager.createNativeQuery("SELECT t.name as \"topicName\", a.name as \"activityName\", count(a.*) as \"quantityOfSubmissions\", " +
				"(SELECT id FROM activity_submission WHERE person_id = :personId AND activity_id = a.id AND selected = 't') as \"submissionId\" " +
				"FROM activity_submission s, person p, topic t, activity a " +
				"WHERE p.id = s.person_id AND s.activity_id = a.id AND a.topic_id = t.id AND s.person_id = :personId " +
				"GROUP BY a.id, t.name ORDER BY t.name, a.id", "report1")
				.setParameter("personId", personId)
				.getResultList();

	}

	public List<Report1> getReport1(Long personId, Long topicId) {
		return entityManager.createNativeQuery("SELECT t.name as \"topicName\", a.name as \"activityName\", count(a.*) as \"quantityOfSubmissions\", " +
				"(SELECT id FROM activity_submission WHERE person_id = :personId AND activity_id = a.id AND selected = 't') as \"submissionId\" " +
				"FROM activity_submission s, person p, topic t, activity a " +
				"WHERE p.id = s.person_id AND s.activity_id = a.id AND a.topic_id = t.id AND s.person_id = :personId AND t.id = :topicId " +
				"GROUP BY a.id, t.name ORDER BY a.id", "report1")
				.setParameter("topicId", topicId).setParameter("personId", personId)
				.getResultList();

	}
}
