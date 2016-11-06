package com.rpl.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rpl.model.DBTest;

public class ActivitySubmissionDAO {
	@PersistenceContext(unitName = "RplPU")
    private EntityManager entityManager;

	
}
