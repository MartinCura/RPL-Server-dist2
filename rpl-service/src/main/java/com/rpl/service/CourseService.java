package com.rpl.service;

import java.util.List;
import java.util.Map;

import com.rpl.exception.RplException;
import com.rpl.model.Course;
import com.rpl.model.CourseImage;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;
import com.rpl.model.Range;
import com.rpl.model.reports.Ranking;

public interface CourseService {

    Course getCourseById(Long id);
    List<Course> getCourses();
    List<Course> getCoursesByRole(String role);
    Course submit(Course course);
    void join(Long courseId);
    void leaveCourse(Long courseId, Long personId);

    void accept(Long courseId, Long personId);
    void pending(Long courseId, Long personId);
    
    List<CoursePerson> getStudents(Long courseId);
    List<CoursePerson> getStudentsByAssistant(Long courseId, Long assistantId);
    List<CoursePerson> getAssistants(Long id);
	void deleteCourseById(Long id);
	void updateCustomization(Long id, String customization);
    void assignAssistant(Long courseId, Long student, Long assistant);
    List<Course> getUnregisteredCourses();
    Map<Long, CoursePerson> getCoursesInscripted();
	void updateCourseName(Long id, String name);
	List<CoursePerson> getProfessors(Long id);
	void updateDescRulesAndCustomization(Long id, String customization, String description, String rules);
    Map<Person,Integer> getPointsByPerson(Long courseId);
	void saveImage(Long courseId, CourseImage courseImage) throws RplException;
	void updateRanges(Long courseId, List<Range> ranges) throws RplException;
	void hide(Long courseId);
	void unhide(Long courseId);
	List<Course> getCoursesEnabledAndDisabled();
    void copyTopics(Long sourceCourseId, Long destCourseId);
    void copyActivities(Long sourceCourseId, Long destCourseId);
	List<Ranking> getRanking(Long courseId);

}
