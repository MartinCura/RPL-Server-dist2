package com.rpl.persistence;

import com.rpl.model.Course;
import com.rpl.model.DatabaseState;

import java.util.List;

public class CourseDAO extends ApplicationDAO {

    public Course find(long id) {
        return entityManager.find(Course.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Course> findAll() {
        return entityManager.createQuery("SELECT c FROM Course c where c.state = :state").setParameter("state",  DatabaseState.ENABLED).getResultList();
    }

	public void delete(Long id) {
		entityManager.createQuery("UPDATE Course set state = :state where id = :id").setParameter("id", id).setParameter("state", DatabaseState.DELETED).executeUpdate();
	}

}
