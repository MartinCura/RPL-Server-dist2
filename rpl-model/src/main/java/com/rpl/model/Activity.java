package com.rpl.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "activity")
@DynamicInsert
@DynamicUpdate
public class Activity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Language language;
    private int points;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    @Enumerated(EnumType.STRING)
    @Column(name="test_type")
    private TestType testType;
    private String template;
    private String input;
    private String output;
    private String tests;
    @Enumerated(EnumType.STRING)
    private DatabaseState state;
    @OneToMany(mappedBy = "activity")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<ActivityInputFile> files;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType type) {
        this.testType = type;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public DatabaseState getState() {
        return state;
    }

    public void setState(DatabaseState state) {
        this.state = state;
    }

    public Set<ActivityInputFile> getFiles() {
        return files;
    }

    public void setFiles(Set<ActivityInputFile> files) {
        this.files = files;
    }

}
