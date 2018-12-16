package com.rpl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
@DynamicInsert
@DynamicUpdate
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String mail;
    @Column(name="student_id")
    private Long studentId;

    @Embedded
    @JsonIgnore
    private Credentials credentials;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_person", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    @JsonIgnore
    private List<Course> courses;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    private Set<ActivitySubmission> submissions;

    @OneToMany(mappedBy = "person")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<CoursePerson> coursePersons;

    @OneToOne(optional = true, mappedBy = "person")
    private PersonImage personImage;

    @Enumerated(EnumType.STRING)
    private DatabaseState state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Set<ActivitySubmission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(Set<ActivitySubmission> submissions) {
        this.submissions = submissions;
    }

    public List<CoursePerson> getCoursePersons() {
        return coursePersons;
    }

    public void setCoursePersons(List<CoursePerson> coursePersons) {
        this.coursePersons = coursePersons;
    }

    public PersonImage getPersonImage() {
        return personImage;
    }

    public void setPersonImage(PersonImage personImage) {
        this.personImage = personImage;
    }

    public DatabaseState getState() {
        return state;
    }

    public void setState(DatabaseState state) {
        this.state = state;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
