package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;
import com.rpl.service.util.Utils;

public class ReportSubmissionPOJO {
    private Long submissionId;
    private String personName;

    public ReportSubmissionPOJO(ActivitySubmission submission) {
        this.submissionId = submission.getId();
        this.personName = Utils.getCompleteName(submission.getPerson());
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public String getPersonName() {
        return personName;
    }
}
