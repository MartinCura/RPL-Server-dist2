package com.rpl.service;

import com.rpl.model.Course;

import java.util.List;


public interface CourseService {

    public Course getCourseById(Long id);
    public List<Course> getCourses();
    public void submit(Course course);
    public void join(Long personId, Long courseId);
}
