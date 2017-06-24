package com.rpl.model.reports;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Report3 {
    private Long studentId;
    private String studentName;
    private String result;

    public Report3(Long studentId, String studentName, String result) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.result = result;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public List<String> getResult() {
        List<String> submissions = Arrays.asList(result.replaceAll("\\{|\\}", "").split(","));
        Collections.replaceAll(submissions,"NULL", null);
        return submissions;

    }
}
