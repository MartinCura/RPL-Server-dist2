package com.rpl.persistence;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.DatabaseState;
import com.rpl.model.Status;

import javax.persistence.Query;
import java.util.List;

public class ActivitySubmissionDAO extends ApplicationDAO{

	public ActivitySubmission find(long id) {
		return entityManager.find(ActivitySubmission.class, id);
	}

    public List<ActivitySubmission> findByPersonAndActivity(Long personId, Long activityId) {
		return entityManager.createQuery(
				"SELECT s FROM ActivitySubmission s " +
						"WHERE s.person.id = :personId AND s.activity.id = :activityId")
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
		return entityManager.createQuery(
				"SELECT s FROM ActivitySubmission s " +
						"WHERE s.person.id = :personId AND s.activity.topic.course.id = :courseId AND " +
						"s.status = :status AND s.selected = 't'")
				.setParameter("personId", personId)
				.setParameter("courseId", courseId)
				.setParameter("status", Status.SUCCESS)
				.getResultList();
    }

    public List<ActivitySubmission> findDefinitiveByActivity(Long activityId) {
		return entityManager.createQuery(
				"SELECT s FROM ActivitySubmission s " +
						"WHERE s.activity.id = :activityId AND s.definitive = 't' AND s.person.state = :state")
				.setParameter("activityId", activityId)
				.setParameter("state", DatabaseState.ENABLED)
				.getResultList();
    }

    public List<ActivitySubmission> findSelectedByCourse(Long courseId) {
		return entityManager.createQuery(
				"SELECT s FROM ActivitySubmission s " +
						"WHERE s.activity.topic.course.id = :courseId AND " +
						"s.status = :status AND s.selected = 't' AND s.person.state = :state")
				.setParameter("courseId", courseId)
				.setParameter("status", Status.SUCCESS)
				.setParameter("state", DatabaseState.ENABLED)
				.getResultList();
    }

	public List<ActivitySubmission> findSelectedByCourseAndAssistant(Long courseId, Long assistantId) {
		return entityManager.createQuery(
				"SELECT s FROM ActivitySubmission s, CoursePerson cp " +
						"WHERE s.person.id = cp.person.id AND s.activity.topic.course.id = cp.course.id AND " +
						"cp.assistant.id = :assistantId AND s.activity.topic.course.id = :courseId AND " +
						"s.status = :status AND s.selected = 't' AND s.person.state = :state")
				.setParameter("courseId", courseId)
				.setParameter("assistantId", assistantId)
				.setParameter("status", Status.SUCCESS)
				.setParameter("state", DatabaseState.ENABLED)
				.getResultList();
	}

	public ActivitySubmission findDefinitiveByActivityAndPerson(Long activityId, Long personId) {
		Query query = entityManager.createQuery(
				"SELECT s FROM ActivitySubmission s " +
						"WHERE s.activity.id = :activityId AND s.person.id = :personId AND s.definitive = 't'")
				.setParameter("activityId", activityId)
				.setParameter("personId", personId);
		return (ActivitySubmission) query.getSingleResult();
	}
}
