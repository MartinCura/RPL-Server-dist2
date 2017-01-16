package com.rpl.service;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface CourseService {

    public Course getCourseById(Long id);
    public List<Course> getCourses();
    public List<Course> getCoursesByRole(String role);
    public Course submit(Course course);
    public void join(Long courseId);

    public Map<Person,Set<ActivitySubmission>> getSubmissionsByStudent(Long id);

    public void accept(Long courseId, Long personId);

    public List<CoursePerson> getStudents(Long id);
    public List<CoursePerson> getAssistants(Long id);
	public void deleteCourseById(Long id);

}
