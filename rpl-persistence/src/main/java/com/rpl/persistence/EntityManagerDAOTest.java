package com.rpl.persistence;

import com.rpl.model.DBTest;

public class EntityManagerDAOTest extends ApplicationDAO{

	public DBTest find(int id) {
		return this.entityManager.find(DBTest.class, id);
	}

}
