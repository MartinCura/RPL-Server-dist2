package com.rpl.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.model.DBTest;
import com.rpl.persistence.EntityManagerDAOTest;

@Stateless
public class EntityManagerTest {

    @Inject
    private EntityManagerDAOTest emTest;

    public DBTest find(){
        return emTest.find(0);
    }
    
    public DBTest save(){
        DBTest test = new DBTest();
        test.setMsg("test");
        return emTest.save(test);
    }
    
}