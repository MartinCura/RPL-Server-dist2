package com.rpl.model.reports;

public class Report2 {
    private String activityName;
    private int points;
    private float average;

    public Report2(String activityName, int points, float average) {
        this.activityName = activityName;
        this.points = points;
        this.average = average;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getPoints() {
        return points;
    }

    public float getAverage() {
        return average;
    }
}
