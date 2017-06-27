package com.rpl.model.reports;

public class Report1 {
    private String topicName;
    private String activityName;
    private int quantityOfSubmissions;
    private int points;
    private Long submissionId;

    public Report1(String topicName, String activityName, int quantityOfSubmissions, int points, Long submissionId) {
        this.topicName = topicName;
        this.activityName = activityName;
        this.points = points;
        this.quantityOfSubmissions = quantityOfSubmissions;
        this.submissionId = submissionId;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getQuantityOfSubmissions() {
        return quantityOfSubmissions;
    }

    public int getPoints() {
        return points;
    }

    public Long getSubmissionId() {
        return submissionId;
    }
}
