package com.rpl.persistence;

import com.rpl.model.Activity;

public class ActivityDAO extends ApplicationDAO {

	public Activity find(long id) {
		return entityManager.find(Activity.class, id);
	}
	
	public Activity save(Activity act){
		return entityManager.merge(act);
	}
	
}
