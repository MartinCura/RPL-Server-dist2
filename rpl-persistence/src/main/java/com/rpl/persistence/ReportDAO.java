package com.rpl.persistence;

import com.rpl.model.reports.*;

import java.util.Date;
import java.util.List;

public class ReportDAO extends ApplicationDAO {

	public List<Report1> getReport1ByCourse(Long personId, Long courseId) {
		return entityManager.createNativeQuery("SELECT t.name as \"topicName\", a.name as \"activityName\", count(a.*) as \"quantityOfSubmissions\", " +
				"(SELECT id FROM activity_submission WHERE person_id = :personId AND activity_id = a.id AND selected = 't') as \"submissionId\" " +
				"FROM activity_submission s, person p, topic t, activity a " +
				"WHERE p.id = s.person_id AND s.activity_id = a.id AND a.topic_id = t.id AND s.person_id = :personId AND t.course_id = :courseId AND a.state = :state " +
				"GROUP BY a.id, t.name ORDER BY t.name, a.id", "report1")
				.setParameter("courseId", courseId).setParameter("personId", personId).setParameter("state", "ENABLED")
				.getResultList();

	}

	public List<Report1> getReport1ByTopic(Long personId, Long topicId) {
		return entityManager.createNativeQuery("SELECT t.name as \"topicName\", a.name as \"activityName\", count(a.*) as \"quantityOfSubmissions\", " +
				"(SELECT id FROM activity_submission WHERE person_id = :personId AND activity_id = a.id AND selected = 't') as \"submissionId\" " +
				"FROM activity_submission s, person p, topic t, activity a " +
				"WHERE p.id = s.person_id AND s.activity_id = a.id AND a.topic_id = t.id AND s.person_id = :personId AND t.id = :topicId AND a.state = :state " +
				"GROUP BY a.id, t.name ORDER BY t.name, a.id", "report1")
				.setParameter("topicId", topicId).setParameter("personId", personId).setParameter("state", "ENABLED")
				.getResultList();

	}

	public List<Report2> getReport2(Long topicId) {
		return entityManager.createNativeQuery("SELECT sq.activity as \"activityName\", avg(sq.count) as average FROM " +
				"(SELECT a.name as activity, s.person_id as person, count(*) " +
				"FROM activity_submission s, activity a " +
				"WHERE s.activity_id = a.id AND a.topic_id = :topicId AND a.state = :state AND exists " +
				"(select 1 from activity_submission where selected = 't' and person_id = s.person_id and activity_id = s.activity_id) " +
				"GROUP BY s.person_id, a.id) sq " +
				"GROUP BY sq.activity ORDER BY sq.activity", "report2")
				.setParameter("topicId", topicId).setParameter("state", "ENABLED")
				.getResultList();

	}

	public List<Report3> getReport3(Long topicId) {
		return entityManager.createNativeQuery("SELECT p.id as \"studentId\", p.name as \"studentName\", " +
				"array_agg((select id from activity_submission where activity_id = a.id and person_id = p.id and selected = 't'))\\:\\:text as result " +
				"FROM activity a, topic t, person p " +
				"WHERE a.state = :state AND a.topic_id = t.id AND t.id = :topicId " +
				"AND exists (select 1 from course_person where person_id = p.id and course_id = t.course_id and role = 'STUDENT') " +
				"GROUP BY p.id ORDER BY p.name", "report3")
				.setParameter("topicId", topicId).setParameter("state", "ENABLED")
				.getResultList();
	}

	public List<Report5> getReport5(Long topicId) {
		return entityManager.createNativeQuery("SELECT p.id as \"studentId\", p.name as \"studentName\", " +
				"round(sum(a.points) / (SELECT SUM(points) FROM activity WHERE topic_id= :topicId AND state= :state)\\:\\:numeric, 2) as percentage " +
				"FROM activity_submission s, activity a, person p " +
				"WHERE s.activity_id = a.id AND s.person_id = p.id AND a.topic_id = :topicId AND s.selected = 't' AND a.state = :state " +
				"GROUP BY p.id ORDER BY percentage desc", "report5").setParameter("state", "ENABLED")
				.setParameter("topicId", topicId)
				.getResultList();
	}

	public List<Report6> getReport6(Long topicId, Long personId) {
		return entityManager.createNativeQuery("SELECT a.name as \"activityName\", a.points as points, count(s.*) as tries " +
				"FROM activity_submission s, activity a, topic t, person p " +
				"WHERE s.activity_id = a.id AND s.person_id = p.id AND a.topic_id = t.id AND a.topic_id = :topicId AND s.person_id = :personId AND a.state = :state " +
				"GROUP BY a.id ORDER BY a.points desc, a.id", "report6")
				.setParameter("topicId", topicId).setParameter("personId", personId).setParameter("state", "ENABLED")
				.getResultList();
	}

	public List<ReportRanking> getReportRanking(Long courseId, Date date) {
		return entityManager.createNativeQuery("SELECT p.name as \"studentName\", sum(a.points) as points " +
				"FROM activity_submission s, activity a, topic t, person p " +
				"WHERE s.activity_id = a.id AND a.topic_id = t.id AND t.course_id = :courseId AND s.person_id = p.id AND s.selected = 't' " +
				"AND a.state = :state AND s.submission_date <= :date GROUP BY p.id ORDER BY points desc;", "reportranking")
				.setParameter("courseId", courseId).setParameter("date", date).setParameter("state", "ENABLED")
				.getResultList();
	}
}
