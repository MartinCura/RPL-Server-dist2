package com.rpl.persistence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.rpl.model.Person;

public class PersonDAO extends ApplicationDAO {

	public Person find(long id) {
		return entityManager.find(Person.class, id);
	}
	
	public Person find(String username) {
		CriteriaBuilder builder = getCriteriaBuilder();
		CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
		Root<Person> root = criteria.from(Person.class);
		criteria.select(root);
		Path<String> usernameAttr = root.get("credentials").get("username");
		criteria.where(builder.equal(usernameAttr, username));
		return entityManager.createQuery( criteria ).getSingleResult();
	}
	
	public Person save(Person p){
		return this.merge(p);
	}
	
}
