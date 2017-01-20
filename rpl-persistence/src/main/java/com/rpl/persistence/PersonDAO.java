package com.rpl.persistence;

import java.util.List;

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
		return entityManager.createQuery(criteria).getSingleResult();
	}

	public void updatePersonToken(String username, String token) {
		entityManager
				.createQuery("update Person p " + "set p.credentials.token = :token "
						+ "where p.credentials.username = :username")
				.setParameter("token", token).setParameter("username", username).executeUpdate();
	}

	public List<Person> findByCourse(Long id) {
		return entityManager
				.createQuery(
						"SELECT p FROM Person p, CoursePerson cp WHERE p.id = cp.person.id AND cp.course.id = :courseId")
				.setParameter("courseId", id).getResultList();
	}

	public void updatePersonInfo(Long id, String name, String mail) {
		entityManager.createQuery("UPDATE Person set mail = :mail, name = :name WHERE id = :id ").setParameter("id", id)
				.setParameter("name", name).setParameter("mail", mail).executeUpdate();
	}

	public void updatePassword(Long id, String password) {
		entityManager.createQuery("UPDATE Person p set p.credentials.password = :password WHERE id = :id ").setParameter("id", id)
		.setParameter("password", password).executeUpdate();
		
	}
}
