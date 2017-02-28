package com.rpl.service;

import java.util.List;
import java.util.Map;

import com.rpl.exception.RplException;
import com.rpl.model.Course;
import com.rpl.model.CourseImage;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;


public interface CourseService {

    public Course getCourseById(Long id);
    public List<Course> getCourses();
    public List<Course> getCoursesByRole(String role);
    public Course submit(Course course);
    public void join(Long courseId);
    public void leaveCourse(Long courseId, Long personId);

    public void accept(Long courseId, Long personId);
    public void pending(Long courseId, Long personId);
    
    public List<CoursePerson> getStudents(Long courseId);
    public List<CoursePerson> getStudentsByAssistant(Long courseId, Long assistantId);
    public List<CoursePerson> getAssistants(Long id);
	public void deleteCourseById(Long id);
	public void updateCustomization(Long id, String customization);
    public void assignAssistant(Long courseId, Long student, Long assistant);
    public List<Course> getUnregisteredCourses();
    public Map<Long, CoursePerson> getCoursesInscripted();
	public void updateCourseName(Long id, String name);
	public List<CoursePerson> getProfessors(Long id);
	public void updateDescRulesAndCustomization(Long id, String customization, String description, String rules);
    public Map<Person,Integer> getPointsByPerson(Long courseId);
	public void saveImage(Long courseId, CourseImage courseImage) throws RplException;
}
