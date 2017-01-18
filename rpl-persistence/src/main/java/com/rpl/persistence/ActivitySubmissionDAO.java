package com.rpl.persistence;

import com.rpl.model.ActivitySubmission;

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
}
