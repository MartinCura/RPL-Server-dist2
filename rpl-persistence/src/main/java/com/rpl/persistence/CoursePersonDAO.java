package com.rpl.persistence;


import com.rpl.model.CoursePerson;

public class CoursePersonDAO extends ApplicationDAO {

    public CoursePerson find(long id) {
        return entityManager.find(CoursePerson.class, id);
    }
}
