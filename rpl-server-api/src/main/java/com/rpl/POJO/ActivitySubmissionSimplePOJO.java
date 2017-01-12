package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;

public class ActivitySubmissionSimplePOJO {
    private Long id;
    private String status;

    public ActivitySubmissionSimplePOJO(ActivitySubmission submission) {
        this.id = submission.getId();
        this.status = submission.getStatus().toString();
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
