package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;

public class ReportSubmissionPOJO {
    private Long submissionId;
    private String personName;

    public ReportSubmissionPOJO(ActivitySubmission submission) {
        this.submissionId = submission.getId();
        this.personName = submission.getPerson().getName();
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public String getPersonName() {
        return personName;
    }
}
