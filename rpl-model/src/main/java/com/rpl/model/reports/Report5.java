package com.rpl.model.reports;

public class Report5 {
    private Long studentId;
    private String studentName;
    private float percentage;

    public Report5(Long studentId, String studentName, float percentage) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.percentage = percentage;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public float getPercentage() {
        return percentage;
    }
}
