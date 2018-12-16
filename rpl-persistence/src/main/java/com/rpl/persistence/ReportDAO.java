package com.rpl.persistence;

import com.rpl.model.reports.*;

import java.util.Date;
import java.util.List;

public class ReportDAO extends ApplicationDAO {
    // ToDo: Change these for createNativeQuery(q, Report#.class) ? Would avoid warning suppression

    @SuppressWarnings("unchecked")
    public List<Report1> getReport1ByCourse(Long personId, Long courseId) {
        String q = "SELECT t.name as \"topicName\", a.id as \"activityId\", a.name as \"activityName\", a.points as points, " +
                "(select count(*) from activity_submission where person_id = :personId and activity_id = a.id) as \"quantityOfSubmissions\", " +
                "(SELECT id FROM activity_submission WHERE person_id = :personId AND activity_id = a.id AND selected = 't') as \"submissionId\" " +
                "FROM activity a, topic t " +
                "WHERE a.topic_id = t.id AND t.course_id = :courseId AND a.state = :state " +
                "ORDER BY t.name, a.points asc, a.id";
        return entityManager.createNativeQuery(q, "report1")
                .setParameter("courseId", courseId)
                .setParameter("personId", personId)
                .setParameter("state", "ENABLED")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report1> getReport1ByTopic(Long personId, Long topicId) {
        String q = "SELECT t.name as \"topicName\", a.id as \"activityId\", a.name as \"activityName\", a.points as points, " +
                "(select count(*) from activity_submission where person_id = :personId and activity_id = a.id) as \"quantityOfSubmissions\", " +
                "(SELECT id FROM activity_submission WHERE person_id = :personId AND activity_id = a.id AND selected = 't') as \"submissionId\" " +
                "FROM activity a, topic t " +
                "WHERE a.topic_id = t.id AND t.id = :topicId AND a.state = :state " +
                "ORDER BY t.name, a.points asc, a.id";
        return entityManager.createNativeQuery(q, "report1")
                .setParameter("topicId", topicId)
                .setParameter("personId", personId)
                .setParameter("state", "ENABLED")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report2> getReport2(Long topicId) {
        String q = "SELECT c.name as \"activityName\", c.points as points, COALESCE(avg(sq.count), 0) as average FROM " +
                "activity c LEFT JOIN (SELECT a.id as id, a.name as activity, a.points as points, s.person_id as person, count(*) " +
                "FROM activity_submission s, activity a " +
                "WHERE s.activity_id = a.id AND a.topic_id = :topicId AND a.state = :state AND exists " +
                "(select 1 from activity_submission where selected = 't' and person_id = s.person_id and activity_id = s.activity_id) " +
                "GROUP BY s.person_id, a.id) sq ON c.id = sq.id WHERE c.topic_id = :topicId AND c.state = :state " +
                "GROUP BY c.name, c.points ORDER BY c.points asc, c.name";
        return entityManager.createNativeQuery(q, "report2")
                .setParameter("topicId", topicId)
                .setParameter("state", "ENABLED")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report3> getReport3(Long topicId) {
        String q = "SELECT p.id as \"studentId\", p.name as \"studentName\", " +
                "array_agg((select id from activity_submission where activity_id = a.id and person_id = p.id and selected = 't') order by a.name)\\:\\:text as result " +
                "FROM activity a, topic t, person p " +
                "WHERE a.state = :state AND a.topic_id = t.id AND t.id = :topicId " +
                "AND exists (select 1 from course_person where person_id = p.id and course_id = t.course_id and role = 'STUDENT') " +
                "GROUP BY p.id ORDER BY p.name";
        return entityManager.createNativeQuery(q, "report3")
                .setParameter("topicId", topicId)
                .setParameter("state", "ENABLED")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report3> getReport3ByAssistant(Long topicId, Long assistantId) {
        String q = "SELECT p.id as \"studentId\", p.name as \"studentName\", " +
                "array_agg((select id from activity_submission where activity_id = a.id and person_id = p.id and selected = 't') order by a.name)\\:\\:text as result " +
                "FROM activity a, topic t, person p " +
                "WHERE a.state = :state AND a.topic_id = t.id AND t.id = :topicId " +
                "AND exists (select 1 from course_person where person_id = p.id and course_id = t.course_id and role = 'STUDENT' and assistant_id = :assistantId) " +
                "GROUP BY p.id ORDER BY p.name";
        return entityManager.createNativeQuery(q, "report3")
                .setParameter("topicId", topicId)
                .setParameter("assistantId", assistantId)
                .setParameter("state", "ENABLED")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report4> getReport4(Long courseId) {
        String q = "SELECT sq.submission_date\\:\\:text as date, sum(sq.count) as \"quantityOfSubmissions\", " +
                "array_agg(sq.name)\\:\\:text as \"studentNames\", array_agg(sq.count)\\:\\:text as \"studentSubmissions\" " +
                "FROM (SELECT s.submission_date, p.name, count(*) " +
                "FROM activity_submission s, activity a, person p WHERE s.person_id = p.id AND s.activity_id = a.id AND s.activity_id in " +
                "	(select a.id from activity a, topic t where a.topic_id = t.id and a.state = :state and t.course_id = :courseId) " +
                "GROUP BY s.submission_date, p.id ORDER BY submission_date asc, count(*) desc, p.id) sq GROUP BY sq.submission_date";
        return entityManager.createNativeQuery(q, "report4")
                .setParameter("courseId", courseId)
                .setParameter("state", "ENABLED")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report4> getReport4ByAssistant(Long courseId, Long assistantId) {
        String q = "SELECT sq.submission_date\\:\\:text as date, sum(sq.count) as \"quantityOfSubmissions\", " +
                "array_agg(sq.name)\\:\\:text as \"studentNames\", array_agg(sq.count)\\:\\:text as \"studentSubmissions\" " +
                "FROM (SELECT s.submission_date, p.name, count(*) " +
                "FROM activity_submission s, activity a, person p WHERE s.person_id = p.id AND s.activity_id = a.id AND s.activity_id in " +
                "	(select a.id from activity a, topic t where a.topic_id = t.id and a.state = :state and t.course_id = :courseId) " +
                "AND p.id IN (select person_id from course_person where course_id  = :courseId and role='STUDENT' and assistant_id = :assistantId)" +
                "GROUP BY s.submission_date, p.id ORDER BY submission_date asc, count(*) desc, p.id) sq GROUP BY sq.submission_date";
        return entityManager.createNativeQuery(q, "report4")
                .setParameter("courseId", courseId)
                .setParameter("state", "ENABLED")
                .setParameter("assistantId", assistantId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report5> getReport5(Long topicId) {
        String q = "SELECT p.id as \"studentId\", p.name as \"studentName\", " +
                "COALESCE(round((SELECT SUM(points) FROM activity a WHERE topic_id = :topicId AND a.state = :state AND exists " +
                "	(select 1 from activity_submission where person_id = p.id and activity_id = a.id and selected='t')) / " +
                "	(SELECT SUM(points) FROM activity WHERE topic_id = :topicId AND state = :state)\\:\\:numeric, 2), 0) as percentage " +
                "FROM person p, topic t WHERE t.id = :topicId AND p.id IN (select person_id from course_person where course_id  = t.course_id and role='STUDENT') " +
                "ORDER BY percentage desc";
        return entityManager.createNativeQuery(q, "report5")
                .setParameter("state", "ENABLED")
                .setParameter("topicId", topicId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report5> getReport5ByAssistant(Long topicId, Long assistantId) {
        String q = "SELECT p.id as \"studentId\", p.name as \"studentName\", " +
                "COALESCE(round((SELECT SUM(points) FROM activity a WHERE topic_id = :topicId AND a.state = :state AND exists " +
                "	(select 1 from activity_submission where person_id = p.id and activity_id = a.id and selected='t')) / " +
                "	(SELECT SUM(points) FROM activity WHERE topic_id = :topicId AND state = :state)\\:\\:numeric, 2), 0) as percentage " +
                "FROM person p, topic t WHERE t.id = :topicId AND p.id IN (select person_id from course_person where course_id  = t.course_id and role='STUDENT' and assistant_id = :assistantId) " +
                "ORDER BY percentage desc";
        return entityManager.createNativeQuery(q, "report5")
                .setParameter("state", "ENABLED")
                .setParameter("topicId", topicId)
                .setParameter("assistantId", assistantId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report6> getReport6(Long topicId, Long personId) {
        String q = "SELECT a.name as \"activityName\", a.points as points, " +
                "(select count(*) from activity_submission where activity_id = a.id and person_id = :personId) as tries " +
                "FROM activity a " +
                "WHERE a.topic_id = :topicId AND a.state = :state " +
                "ORDER BY a.points asc, a.id";
        return entityManager.createNativeQuery(q, "report6")
                .setParameter("topicId", topicId)
                .setParameter("personId", personId)
                .setParameter("state", "ENABLED")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report7> getReport7(Long courseId, Date date) {
        String q = "SELECT p.name as \"studentName\", " +
                "COALESCE(sum((select sum(points) FROM activity_submission s, activity a, topic t " +
                "	WHERE s.activity_id = a.id AND a.state = :state AND a.topic_id = t.id AND t.course_id = :courseId AND s.person_id = p.id AND s.selected = 't' " +
                "	AND s.submission_date <= :date)), 0) as points " +
                "FROM person p, course c  " +
                "WHERE c.id = :courseId AND p.id IN (select person_id from course_person where course_id  = c.id and role='STUDENT') GROUP BY p.name ORDER BY points desc;";
        return entityManager.createNativeQuery(q, "report7")
                .setParameter("courseId", courseId)
                .setParameter("date", date)
                .setParameter("state", "ENABLED")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Report7> getReport7ByAssistant(Long courseId, Date date, Long assistantId) {
        String q = "SELECT p.name as \"studentName\", " +
                "COALESCE(sum((select sum(points) FROM activity_submission s, activity a, topic t " +
                "	WHERE s.activity_id = a.id AND a.state = :state AND a.topic_id = t.id AND t.course_id = :courseId AND s.person_id = p.id AND s.selected = 't' " +
                "	AND s.submission_date <= :date)), 0) as points " +
                "FROM person p, course c  " +
                "WHERE c.id = :courseId AND p.id IN (select person_id from course_person where course_id  = c.id and role='STUDENT' and assistant_id = :assistantId) " +
                "GROUP BY p.name ORDER BY points desc;";
        return entityManager.createNativeQuery(q, "report7")
                .setParameter("courseId", courseId)
                .setParameter("assistantId", assistantId)
                .setParameter("date", date)
                .setParameter("state", "ENABLED")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Ranking> getRanking(Long courseId) {
        String q = "SELECT p.id as \"personId\", p.name as name, p.username as username, "
                + "COALESCE(sum((select sum(points) FROM activity_submission s, activity a, topic t WHERE s.activity_id = a.id AND "
                + "a.state = :state AND a.topic_id = t.id AND t.course_id = :courseId AND s.person_id = p.id AND s.selected = 't')), 0) as points, "
                + "(select range_name from course_range where course_id = :courseId and min_score < "
                + "(COALESCE(sum((select sum(points) FROM activity_submission s, activity a, topic t WHERE s.activity_id = a.id AND "
                + "a.state = :state AND a.topic_id = t.id AND t.course_id = :courseId AND s.person_id = p.id AND s.selected = 't')), 0)) "
                + "order by min_score desc limit 1) as \"rangeName\" "
                + "FROM person p, course c WHERE c.id = :courseId AND p.id IN (select person_id from course_person where course_id  = c.id and role='STUDENT') "
                + "GROUP BY p.id ORDER BY points desc;";
        return entityManager.createNativeQuery(q, "ranking")
                .setParameter("courseId", courseId)
                .setParameter("state", "ENABLED")
                .getResultList();
    }
}
