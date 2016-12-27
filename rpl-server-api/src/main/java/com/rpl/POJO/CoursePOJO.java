package com.rpl.POJO;

import com.rpl.model.Course;
import com.rpl.model.Person;

import java.util.ArrayList;
import java.util.List;

public class CoursePOJO {

    private String name;
    private List<Long> professors;
    private List<Long> students;

    public CoursePOJO(Course course) {
        this.name = course.getName();
        this.professors = new ArrayList<Long>();
        this.students = new ArrayList<Long>();
        for (Person person : course.getProfessors()) {
            this.professors.add(person.getId());
        }
        for (Person person : course.getStudents()) {
            this.students.add(person.getId());
        }
    }

    public String getName() {
        return name;
    }

    public List<Long> getProfessors() {
        return professors;
    }

    public List<Long> getStudents() {
        return students;
    }
}
