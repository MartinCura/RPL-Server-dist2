package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;

public class ActivitySubmissionSimplePOJO {
    private Long id;
    private String status;
    private boolean selected;

    public ActivitySubmissionSimplePOJO(ActivitySubmission submission) {
        this.id = submission.getId();
        this.status = submission.getStatus().toString();
        this.selected = submission.isSelected();
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }


    public boolean isSelected() {
        return selected;
    }
}
