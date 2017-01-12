package com.rpl.persistence;


import com.rpl.model.CoursePerson;
import com.rpl.model.RoleCourse;

import javax.persistence.Query;
import java.util.List;

public class CoursePersonDAO extends ApplicationDAO {

    public CoursePerson find(long id) {
        return entityManager.find(CoursePerson.class, id);
    }

    public CoursePerson findByCourseAndPerson(Long courseId, Long personId) {
        Query query = entityManager.createQuery(
                "SELECT cp FROM CoursePerson cp " +
                        "WHERE cp.course.id = :courseId AND cp.person.id = :personId")
                .setParameter("courseId", courseId)
                .setParameter("personId", personId);
        return (CoursePerson) query.getSingleResult();
    }

    public List<CoursePerson> findByCourseIdAndRole(Long courseId, RoleCourse role) {
        return entityManager.createQuery(
                "SELECT cp FROM CoursePerson cp WHERE " +
                        "cp.course.id = :courseId AND cp.role = :role")
                .setParameter("courseId", courseId)
                .setParameter("role", role)
                .getResultList();
    }
}
