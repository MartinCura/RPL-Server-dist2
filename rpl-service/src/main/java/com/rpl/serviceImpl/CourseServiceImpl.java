package com.rpl.serviceImpl;

import com.rpl.model.Course;
import com.rpl.persistence.CourseDAO;
import com.rpl.service.CourseService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CourseServiceImpl implements CourseService{

    @Inject
    private CourseDAO courseDAO;

    public List<Course> getCourses() {
        return courseDAO.findAll();
    }

    public Course getCourseById(Long id) {
        return courseDAO.find(id);
    }

    public void submit(Course course) {
        courseDAO.save(course);

    }
}
