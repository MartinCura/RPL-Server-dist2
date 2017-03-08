package com.rpl.persistence;

import java.util.List;

import javax.persistence.NoResultException;

import com.rpl.model.Course;
import com.rpl.model.CourseImage;
import com.rpl.model.DatabaseState;
import com.rpl.model.Range;
import com.rpl.model.RoleCourse;

public class CourseDAO extends ApplicationDAO {

	public Course find(long id) {
		return entityManager.find(Course.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Course> findAll() {
		return entityManager.createQuery("SELECT c FROM Course c where c.state = :state")
				.setParameter("state", DatabaseState.ENABLED).getResultList();
	}

	public void delete(Long id) {
		entityManager.createQuery("UPDATE Course set state = :state where id = :id").setParameter("id", id)
				.setParameter("state", DatabaseState.DELETED).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Course> findByPersonRole(Long personId, RoleCourse role) {
		return entityManager
				.createQuery("SELECT c FROM Course c, CoursePerson cp WHERE"
						+ " c.id = cp.course.id AND cp.person.id = :personId AND cp.role = :role AND c.state = :state")
				.setParameter("personId", personId).setParameter("role", role)
				.setParameter("state", DatabaseState.ENABLED).getResultList();
	}

	public void updateCustomization(Long id, String customization) {
		entityManager.createQuery("UPDATE Course set customization = :customization where id = :id")
				.setParameter("id", id).setParameter("customization", customization).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Course> findUnregisteredByPerson(Long personId) {
		return entityManager
				.createQuery("SELECT c FROM Course c WHERE"
						+ " c.id NOT IN (SELECT cp.course.id FROM CoursePerson cp WHERE cp.person.id = :personId)")
				.setParameter("personId", personId).getResultList();
	}

	public void updateCourseName(Long id, String name) {
		entityManager.createQuery("UPDATE Course set name = :name where id = :id").setParameter("id", id)
				.setParameter("name", name).executeUpdate();
	}

	public void updateDescRulesAndCustomization(Long id, String customization, String description, String rules) {
		entityManager
				.createQuery(
						"UPDATE Course set customization = :customization, description = :description, rules = :rules where id = :id")
				.setParameter("id", id).setParameter("customization", customization)
				.setParameter("description", description).setParameter("rules", rules).executeUpdate();
	}

	public CourseImage findFile(Long fileId) {
		return entityManager.find(CourseImage.class, fileId);
	}

	public CourseImage findFileByCourse(Long courseId) {
		try {
			return (CourseImage) entityManager
					.createQuery(
							"SELECT file FROM CourseImage file WHERE file.course.id = :courseId")
					.setParameter("courseId", courseId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

}
