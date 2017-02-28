package com.rpl.persistence;


import com.rpl.model.CoursePerson;
import com.rpl.model.DatabaseState;
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
                        "WHERE cp.course.id = :courseId AND cp.person.id = :personId AND cp.state = :state")
                .setParameter("courseId", courseId)
                .setParameter("state", DatabaseState.ENABLED)
                .setParameter("personId", personId);
        return (CoursePerson) query.getSingleResult();
    }
    
    public List<CoursePerson> findByCourseIdAndRole(Long courseId, RoleCourse role) {
        return entityManager.createQuery(
                "SELECT cp FROM CoursePerson cp WHERE " +
                        "cp.course.id = :courseId AND cp.role = :role AND cp.state = :state")
                .setParameter("courseId", courseId)
                .setParameter("state", DatabaseState.ENABLED)
                .setParameter("role", role)
                .getResultList();
    }

    public void deleteByPersonId(Long courseId, Long personId){
    	entityManager.createQuery("UPDATE CoursePerson cp set state = :state where cp.person.id = :personId and cp.course.id = :courseId").setParameter("personId", personId)
    	.setParameter("courseId", courseId).setParameter("state", DatabaseState.DELETED).executeUpdate();
    }
    
    public void acceptStudent(Long courseId, Long personId) {
        entityManager.createQuery("UPDATE CoursePerson c set c.accepted = true WHERE " +
                "c.person.id = :personId AND c.course.id = :courseId")
                .setParameter("personId", personId)
                .setParameter("courseId", courseId).executeUpdate();
    }
    
    public void pendingStudent(Long courseId, Long personId) {
        entityManager.createQuery("UPDATE CoursePerson c set c.accepted = false WHERE " +
                "c.person.id = :personId AND c.course.id = :courseId")
                .setParameter("personId", personId)
                .setParameter("courseId", courseId).executeUpdate();
    }

    public void updateAssistant(Long courseId, Long student, Long assistant) {
        entityManager.createQuery("UPDATE CoursePerson c set c.assistant.id = :assistantId WHERE " +
                "c.person.id = :personId AND c.course.id = :courseId")
                .setParameter("assistantId", assistant)
                .setParameter("personId", student)
                .setParameter("courseId", courseId).executeUpdate();
    }

    public List<CoursePerson> findByPerson(Long personId) {
        return entityManager.createQuery(
                "SELECT cp FROM CoursePerson cp WHERE " +
                        "cp.person.id = :personId AND cp.state = :state")
                .setParameter("personId", personId)
                .setParameter("state", DatabaseState.ENABLED)
                .getResultList();
    }

    public List<CoursePerson> findStudentsByAssistant(Long courseId, Long assistantId) {
        return entityManager.createQuery(
                "SELECT cp FROM CoursePerson cp " +
                        "WHERE cp.course.id = :courseId AND cp.assistant.id = :assistantId AND cp.state = :state")
                .setParameter("courseId", courseId)
                .setParameter("state", DatabaseState.ENABLED)
                .setParameter("assistantId", assistantId)
                .getResultList();
    }
}
