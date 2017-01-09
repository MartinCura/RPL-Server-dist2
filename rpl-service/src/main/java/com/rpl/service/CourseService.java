package com.rpl.service;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.Person;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface CourseService {

    public Course getCourseById(Long id);
    public List<Course> getCourses();
    public void submit(Course course);
    public void join(Long personId, Long courseId);

    Map<Person,Set<ActivitySubmission>> getSubmissionsByStudent(Long id);

    public void accept(Long courseId, Long personId);
}
