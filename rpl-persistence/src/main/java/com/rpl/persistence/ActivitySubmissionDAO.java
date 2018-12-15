package com.rpl.persistence;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.DatabaseState;
import com.rpl.model.Status;

import java.util.List;

public class ActivitySubmissionDAO extends ApplicationDAO{

	public ActivitySubmission find(long id) {
		return entityManager.find(ActivitySubmission.class, id);
	}

    public List<ActivitySubmission> findByPersonAndActivity(Long personId, Long activityId) {
		String q = "SELECT s FROM ActivitySubmission s " +
				"WHERE s.person.id = :personId AND s.activity.id = :activityId ORDER BY s.id";
		return entityManager.createQuery(q, ActivitySubmission.class)
				.setParameter("personId", personId)
				.setParameter("activityId", activityId)
				.getResultList();
    }

    public void setUnselectedSubmissions(Long submissionId, Long personId, Long activityId) {
		entityManager.createQuery("UPDATE ActivitySubmission set selected = 'f'" +
				"WHERE id != :submissionId AND person.id = :personId AND activity.id = :activityId")
				.setParameter("submissionId", submissionId)
				.setParameter("personId", personId)
				.setParameter("activityId", activityId)
				.executeUpdate();
    }

    public List<ActivitySubmission> findSelectedByPersonAndCourse(Long personId, Long courseId) {
		String q = "SELECT s FROM ActivitySubmission s " +
				"WHERE s.person.id = :personId AND s.activity.topic.course.id = :courseId AND " +
				"s.status = :status AND s.selected = 't'";
		return entityManager.createQuery(q, ActivitySubmission.class)
				.setParameter("personId", personId)
				.setParameter("courseId", courseId)
				.setParameter("status", Status.SUCCESS)
				.getResultList();
    }

    public List<ActivitySubmission> findDefinitiveByActivity(Long activityId) {
		String q = "SELECT s FROM ActivitySubmission s " +
				"WHERE s.activity.id = :activityId AND s.definitive = 't' AND s.person.state = :state";
		return entityManager.createQuery(q, ActivitySubmission.class)
				.setParameter("activityId", activityId)
				.setParameter("state", DatabaseState.ENABLED)
				.getResultList();
    }

    public List<ActivitySubmission> findSelectedByCourse(Long courseId) {
		String q = "SELECT s FROM ActivitySubmission s " +
				"WHERE s.activity.topic.course.id = :courseId AND s.activity.state = :activityState AND " +
				"s.status = :status AND s.selected = 't' AND s.person.state = :personState";
		return entityManager.createQuery(q, ActivitySubmission.class)
				.setParameter("courseId", courseId)
				.setParameter("status", Status.SUCCESS)
				.setParameter("activityState", DatabaseState.ENABLED)
				.setParameter("personState", DatabaseState.ENABLED)
				.getResultList();
    }

	public List<ActivitySubmission> findSelectedByCourseAndAssistant(Long courseId, Long assistantId) {
		String q = "SELECT s FROM ActivitySubmission s, CoursePerson cp " +
				"WHERE s.person.id = cp.person.id AND s.activity.topic.course.id = cp.course.id AND " +
				"cp.assistant.id = :assistantId AND s.activity.topic.course.id = :courseId AND " +
				"s.activity.state = :activityState AND s.status = :status AND s.selected = 't' AND s.person.state = :personState";
		return entityManager.createQuery(q, ActivitySubmission.class)
				.setParameter("courseId", courseId)
				.setParameter("assistantId", assistantId)
				.setParameter("status", Status.SUCCESS)
				.setParameter("activityState", DatabaseState.ENABLED)
				.setParameter("personState", DatabaseState.ENABLED)
				.getResultList();
	}

	public ActivitySubmission findDefinitiveByActivityAndPerson(Long activityId, Long personId) {
		String q = "SELECT s FROM ActivitySubmission s " +
				"WHERE s.activity.id = :activityId AND s.person.id = :personId AND s.definitive = 't'";
		return entityManager.createQuery(q, ActivitySubmission.class)
				.setParameter("activityId", activityId)
				.setParameter("personId", personId)
				.getSingleResult();
	}
}
