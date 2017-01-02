package com.rpl.persistence;

import com.rpl.model.Course;

import java.util.List;

public class CourseDAO extends ApplicationDAO {

    public Course find(long id) {
        return entityManager.find(Course.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Course> findAll() {
        return entityManager.createQuery("SELECT c FROM Course c").getResultList();
    }

}
