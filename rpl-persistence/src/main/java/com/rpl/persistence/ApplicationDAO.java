package com.rpl.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

public class ApplicationDAO {
	
	@PersistenceContext(unitName = "RplPU")
    protected EntityManager entityManager;
	
	private static Boolean MANAGED_CONTAINER = true;

	public ApplicationDAO() {
		/*
		 * We need the persistence layer to work on a managed container and in a standalone
		 * application, so if the entityManager wasnt injected we need to create it 
		 * programmatically
		 * */
		if (!MANAGED_CONTAINER) {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("RplPU");
			this.entityManager = factory.createEntityManager();
		}
	}
	
	public static void setBeanTransactionManagement(){
		MANAGED_CONTAINER = false;
	}
	
	public <T> T save(T obj){
		if (MANAGED_CONTAINER) return entityManager.merge(obj);
		entityManager.getTransaction().begin();
		T result = entityManager.merge(obj);
		entityManager.getTransaction().commit();
		return result;
	}
	
	public CriteriaBuilder getCriteriaBuilder() {
		return entityManager.getCriteriaBuilder();
	}

	public void clear() {
		entityManager.clear();
	}

}
