package com.rpl.model.reports;

public class Report2 {
    private String activityName;
    private float average;

    public Report2(String activityName, float average) {
        this.activityName = activityName;
        this.average = average;
    }

    public String getActivityName() {
        return activityName;
    }

    public float getAverage() {
        return average;
    }
}
