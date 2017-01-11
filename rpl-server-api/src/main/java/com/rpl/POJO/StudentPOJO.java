package com.rpl.POJO;


import com.rpl.model.CoursePerson;

public class StudentPOJO {
    private Long id;
    private String name;
    private boolean accepted;
    private Long assistant;

    public StudentPOJO(CoursePerson student) {
        this.id = student.getId();
        this.name = student.getPerson().getName();
        this.accepted = student.isAccepted();
        if (student.getAssistant() != null) {
            this.assistant = student.getAssistant().getId();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public Long getAssistant() {
        return assistant;
    }
}
