package com.rpl.POJO;

import com.rpl.model.CoursePerson;

public class AssistantPOJO {
    private Long id;
    private String name;

    public AssistantPOJO(CoursePerson person) {
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
