package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;

public class ActivitySubmissionPOJO {
    private Long id;
    private Long activity;
    private String code;
    private String status;
    private ResultPOJO result;
    private boolean selected;
    private boolean definitive;

    public ActivitySubmissionPOJO(ActivitySubmission submission) {
        this.id = submission.getId();
        this.activity = submission.getActivity().getId();
        this.code = submission.getCode();
        this.status = submission.getStatus().toString();
        this.selected = submission.isSelected();
        this.definitive = submission.isDefinitive();
        if (submission.getResult() != null)
            this.result = new ResultPOJO(submission.getResult());
    }

    public Long getId() {
        return id;
    }

    public Long getActivity() {
        return activity;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public ResultPOJO getResult() {
        return result;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isDefinitive() {
        return definitive;
    }
}
