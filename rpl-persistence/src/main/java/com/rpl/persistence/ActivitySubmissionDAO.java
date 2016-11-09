package com.rpl.persistence;

import com.rpl.model.ActivitySubmission;

public class ActivitySubmissionDAO extends ApplicationDAO{

	public ActivitySubmission find(long id) {
		return entityManager.find(ActivitySubmission.class, id);
	}
	
	public ActivitySubmission save(ActivitySubmission act){
		return entityManager.merge(act);
	}
	
	
}
