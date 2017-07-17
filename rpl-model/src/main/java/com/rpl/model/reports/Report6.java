package com.rpl.model.reports;

public class Report6 {
    private String activityName;
    private int points;
    private int tries;

    public Report6(String activityName, int points, int tries) {
        this.activityName = activityName;
        this.points = points;
        this.tries = tries;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getPoints() {
        return points;
    }

    public int getTries() {
        return tries;
    }
}
