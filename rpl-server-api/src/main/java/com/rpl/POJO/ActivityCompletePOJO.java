package com.rpl.POJO;

import com.rpl.model.Activity;

public class ActivityCompletePOJO {
    private Long id;
    private String name;
    private String description;
    private String language;
    private int points;
    private Long topic;
    private String testType;
    private String template;
    private String input;
    private String output;
    private String tests;

    public ActivityCompletePOJO(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.description = activity.getDescription();
        this.language = activity.getLanguage().toString();
        this.points = activity.getPoints();
        this.topic = activity.getTopic().getId();
        this.testType = activity.getTestType().toString();
        this.template = activity.getTemplate();
        this.input = activity.getInput();
        this.output = activity.getOutput();
        this.tests = activity.getTests();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public int getPoints() {
        return points;
    }

    public Long getTopic() {
        return topic;
    }

    public String getTestType() {
        return testType;
    }

    public String getTemplate() {
        return template;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public String getTests() {
        return tests;
    }
}
