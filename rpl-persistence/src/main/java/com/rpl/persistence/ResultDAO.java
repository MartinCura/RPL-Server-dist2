package com.rpl.persistence;

import com.rpl.model.runner.Result;

public class ResultDAO extends ApplicationDAO {
	
	public Result find(long id) {
		return entityManager.find(Result.class, id);
	}
	
}
