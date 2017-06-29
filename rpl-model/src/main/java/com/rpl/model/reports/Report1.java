package com.rpl.model.reports;

public class Report1 {
    private String topicName;
    private Long activityId;
    private String activityName;
    private int quantityOfSubmissions;
    private int points;
    private Long submissionId;

    public Report1(String topicName, Long activityId, String activityName, int quantityOfSubmissions, int points, Long submissionId) {
        this.topicName = topicName;
        this.activityId = activityId;
        this.activityName = activityName;
        this.points = points;
        this.quantityOfSubmissions = quantityOfSubmissions;
        this.submissionId = submissionId;
    }

    public String getTopicName() {
        return topicName;
    }

    public Long getActivityId() {
        return activityId;
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
