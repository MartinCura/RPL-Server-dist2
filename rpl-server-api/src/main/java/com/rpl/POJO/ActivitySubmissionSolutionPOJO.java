package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;

public class ActivitySubmissionSolutionPOJO {
    private String name;
    private String solution;

    public ActivitySubmissionSolutionPOJO(ActivitySubmission submission) {
        this.name = submission.getPerson().getName();
        this.solution = submission.getCode();
    }

    public String getName() {
        return name;
    }

    public String getSolution() {
        return solution;
    }
}
