package com.rpl.service;

import javax.inject.Inject;

import com.rpl.model.DBTest;
import com.rpl.persistence.EntityManagerDAOTest;

public class EntityManagerTest {

	@Inject
	private EntityManagerDAOTest emTest;
	
    public DBTest find(){
    	return emTest.find(0);
    }
    
}