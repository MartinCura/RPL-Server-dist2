package com.rpl.model.reports;

public class Report1 {
    private String topicName;
    private String activityName;
    private int quantityOfSubmissions;
    private Long submissionId;

    public Report1(String topicName, String activityName, int quantityOfSubmissions, Long submissionId) {
        this.topicName = topicName;
        this.activityName = activityName;
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

    public Long getSubmissionId() {
        return submissionId;
    }
}
