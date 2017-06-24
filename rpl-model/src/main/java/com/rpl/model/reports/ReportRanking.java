package com.rpl.model.reports;

public class ReportRanking {
    private String studentName;
    private int points;

    public ReportRanking(String studentName, int points) {
        this.studentName = studentName;
        this.points = points;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getPoints() {
        return points;
    }
}
