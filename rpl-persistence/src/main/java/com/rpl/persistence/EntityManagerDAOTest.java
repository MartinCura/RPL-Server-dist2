package com.rpl.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rpl.model.DBTest;

public class EntityManagerDAOTest {
	@PersistenceContext(unitName = "RplPU")
    private EntityManager entityManager;

	public DBTest find(int id) {
		return entityManager.find(DBTest.class, id);
	}
	
	
}
