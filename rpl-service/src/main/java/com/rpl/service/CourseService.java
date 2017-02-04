package com.rpl.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;


public interface CourseService {

    public Course getCourseById(Long id);
    public List<Course> getCourses();
    public List<Course> getCoursesByRole(String role);
    public Course submit(Course course);
    public void join(Long courseId);
    public void leaveCourse(Long personId);
    
    public Map<Person,Set<ActivitySubmission>> getSubmissionsByStudent(Long id);

    public void accept(Long courseId, Long personId);

    public List<CoursePerson> getStudents(Long id);
    public List<CoursePerson> getAssistants(Long id);
	public void deleteCourseById(Long id);
	public void updateCustomization(Long id, String customization);
    public void assignAssistant(Long courseId, Long student, Long assistant);
    public List<Course> getUnregisteredCourses();
    public Map<Long, Boolean> getCoursesInscripted();
	public void updateCourseName(Long id, String name);
	public List<CoursePerson> getProfessors(Long id);
	
}
