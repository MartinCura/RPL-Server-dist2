package com.rpl.POJO;

import com.rpl.model.CoursePerson;

public class ProfessorPOJO {
    private Long id;
    private String name;

    public ProfessorPOJO(CoursePerson person) {
        this.id = person.getPerson().getId();
        this.name = person.getPerson().getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
