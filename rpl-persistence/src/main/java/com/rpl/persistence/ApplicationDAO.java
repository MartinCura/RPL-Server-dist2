package com.rpl.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

public class ApplicationDAO {
	
	@PersistenceContext(unitName = "RplPU")
    protected EntityManager entityManager;

	public ApplicationDAO() {
		/*
		 * We need the persistence layer to work on a managed container and in a standalone
		 * application, so if the entityManager wasnt injected we need to create it 
		 * programmatically
		 * */
		if (this.entityManager == null) {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("RplPU");
			this.entityManager = factory.createEntityManager();
		}
	}
	
	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

}
