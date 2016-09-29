package com.rpl.service;

import com.rpl.model.DBTest;

public class EntityManagerTest {

    /*@PersistenceContext(unitName = "RplPU")
    private EntityManager entityManager;*/
    
    public DBTest find(){
    	return new DBTest();
    }
    
}