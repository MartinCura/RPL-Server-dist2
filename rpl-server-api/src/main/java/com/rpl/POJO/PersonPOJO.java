package com.rpl.POJO;

import com.rpl.model.Course;
import com.rpl.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonPOJO {

    private String name;
    private String mail;
    private List<Long> courses;

    public PersonPOJO(Person person) {
        this.name = person.getName();
        this.mail = person.getMail();
        this.courses = new ArrayList<Long>();
        for (Course course : person.getCourses()) {
            this.courses.add(course.getId());
        }
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public List<Long> getCourses() {
        return courses;
    }
}
