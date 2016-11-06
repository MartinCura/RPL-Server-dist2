package com.rpl.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rpl.model.ActivitySubmission;

public class ActivitySubmissionDAO {
	@PersistenceContext(unitName = "RplPU")
    private EntityManager entityManager;

	public ActivitySubmission find(long id) {
		return entityManager.find(ActivitySubmission.class, id);
	}
	
	public ActivitySubmission save(ActivitySubmission act){
		return entityManager.merge(act);
	}
	
	
}
