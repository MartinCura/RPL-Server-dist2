package com.rpl.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.rpl.model.DBTest;

public class EntityManagerTest {

    @PersistenceContext(unitName = "RplPU")
    private EntityManager entityManager;
    
    public DBTest find(){
    	return entityManager.find(DBTest.class, 0);
    }
    
}