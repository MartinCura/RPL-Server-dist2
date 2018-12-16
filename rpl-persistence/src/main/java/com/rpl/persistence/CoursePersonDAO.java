package com.rpl.persistence;


import com.rpl.model.CoursePerson;
import com.rpl.model.DatabaseState;
import com.rpl.model.RoleCourse;

import java.util.List;

public class CoursePersonDAO extends ApplicationDAO {

    public CoursePerson find(long id) {
        return entityManager.find(CoursePerson.class, id);
    }

    public CoursePerson findByCourseAndPerson(Long courseId, Long personId) {
        String q = "SELECT cp FROM CoursePerson cp " +
                "WHERE cp.course.id = :courseId AND cp.person.id = :personId AND cp.state = :stateCP AND cp.person.state = :stateP";
        return entityManager.createQuery(q, CoursePerson.class)
                .setParameter("courseId", courseId)
                .setParameter("stateCP", DatabaseState.ENABLED)
                .setParameter("stateP", DatabaseState.ENABLED)
                .setParameter("personId", personId)
                .getSingleResult();
    }
    
    public List<CoursePerson> findByCourseIdAndRole(Long courseId, RoleCourse role) {
        String q = "SELECT cp FROM CoursePerson cp, Person p WHERE cp.person.id = p.id AND " +
                "cp.course.id = :courseId AND cp.role = :role AND cp.state = :stateCP AND cp.person.state = :stateP ORDER BY p.name";
        return entityManager.createQuery(q, CoursePerson.class)
                .setParameter("courseId", courseId)
                .setParameter("stateCP", DatabaseState.ENABLED)
                .setParameter("stateP", DatabaseState.ENABLED)
                .setParameter("role", role)
                .getResultList();
    }

    public void deleteByPersonId(Long courseId, Long personId){
        entityManager.createQuery("DELETE FROM CoursePerson cp WHERE cp.person.id = :personId and cp.course.id = :courseId")
                .setParameter("personId", personId)
                .setParameter("courseId", courseId)
                .executeUpdate();
    }
    
    public void acceptStudent(Long courseId, Long personId) {
        entityManager.createQuery("UPDATE CoursePerson c set c.accepted = true WHERE " +
                "c.person.id = :personId AND c.course.id = :courseId")
                .setParameter("personId", personId)
                .setParameter("courseId", courseId)
                .executeUpdate();
    }
    
    public void pendingStudent(Long courseId, Long personId) {
        entityManager.createQuery("UPDATE CoursePerson c set c.accepted = false WHERE " +
                "c.person.id = :personId AND c.course.id = :courseId")
                .setParameter("personId", personId)
                .setParameter("courseId", courseId)
                .executeUpdate();
    }

    public void updateAssistant(Long courseId, Long student, Long assistant) {
        entityManager.createQuery("UPDATE CoursePerson c set c.assistant.id = :assistantId WHERE " +
                "c.person.id = :personId AND c.course.id = :courseId")
                .setParameter("assistantId", assistant)
                .setParameter("personId", student)
                .setParameter("courseId", courseId)
                .executeUpdate();
    }

    public List<CoursePerson> findByPerson(Long personId) {
        String q = "SELECT cp FROM CoursePerson cp WHERE " +
                "cp.person.id = :personId AND cp.state = :stateCP AND cp.person.state = :stateP";
        return entityManager.createQuery(q, CoursePerson.class)
                .setParameter("personId", personId)
                .setParameter("stateCP", DatabaseState.ENABLED)
                .setParameter("stateP", DatabaseState.ENABLED)
                .getResultList();
    }

    public List<CoursePerson> findStudentsByAssistant(Long courseId, Long assistantId) {
        String q = "SELECT cp FROM CoursePerson cp, Person p WHERE cp.person.id = p.id AND " +
                "cp.course.id = :courseId AND cp.assistant.id = :assistantId " +
                "AND cp.state = :stateCP AND cp.person.state = :stateP ORDER BY p.name";
        return entityManager.createQuery(q, CoursePerson.class)
                .setParameter("courseId", courseId)
                .setParameter("stateCP", DatabaseState.ENABLED)
                .setParameter("stateP", DatabaseState.ENABLED)
                .setParameter("assistantId", assistantId)
                .getResultList();
    }

}
