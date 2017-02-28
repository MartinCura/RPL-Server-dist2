package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;

public class ActivitySubmissionSimplePOJO {
    private Long id;
    private String status;
    private boolean selected;
    private boolean definitive;

    public ActivitySubmissionSimplePOJO(ActivitySubmission submission) {
        this.id = submission.getId();
        this.status = submission.getStatus().toString();
        this.selected = submission.isSelected();
        this.definitive = submission.isDefinitive();
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

    public boolean isDefinitive() {
        return definitive;
    }
}
