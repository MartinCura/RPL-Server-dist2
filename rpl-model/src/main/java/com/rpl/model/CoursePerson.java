package com.rpl.model;


import javax.persistence.*;

@Entity
@Table(name = "course_person")
public class CoursePerson {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private RoleCourse role;
    private boolean accepted;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assistant_id")
    private Person assistant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public RoleCourse getRole() {
        return role;
    }

    public void setRole(RoleCourse role) {
        this.role = role;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Person getAssistant() {
        return assistant;
    }

    public void setAssistant(Person assistant) {
        this.assistant = assistant;
    }
}
