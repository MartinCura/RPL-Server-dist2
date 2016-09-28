package com.rpl.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EntityManagerTest {

    @PersistenceContext(unitName = "RplPU")
    private EntityManager entityManager;

}