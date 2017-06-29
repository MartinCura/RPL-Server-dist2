package com.rpl.model.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Report4 {
    private String date;
    private int quantityOfSubmissions;
    private String studentNames;
    private String studentSubmissions;

    public Report4(String date, int quantityOfSubmissions, String studentNames, String studentSubmissions) {
        this.date = date;
        this.quantityOfSubmissions = quantityOfSubmissions;
        this.studentNames = studentNames;
        this.studentSubmissions = studentSubmissions;
    }

    public String getDate() {
        return date;
    }

    public int getQuantityOfSubmissions() {
        return quantityOfSubmissions;
    }

    public List<Report4Student> getStudents() {
        List<String> names = Arrays.asList(studentNames.replaceAll("\\{|\\}|\\\"", "").split(","));
        List<String> submissions = Arrays.asList(studentSubmissions.replaceAll("\\{|\\}", "").split(","));
        List<Report4Student> students = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            students.add(new Report4Student(names.get(i), Integer.valueOf(submissions.get(i))));
        }

        return students;
    }

    public class Report4Student {
        private String studentName;
        private int submission;

        public Report4Student(String studentName, int submission) {
            this.studentName = studentName;
            this.submission = submission;
        }

        public String getStudentName() {
            return studentName;
        }

        public int getSubmission() {
            return submission;
        }
    }
}
