package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.runner.Result;

public class ActivitySubmissionPOJO {
    private Long id;
    private Long activity;
    private String code;
    private String status;
    private Result result;

    public ActivitySubmissionPOJO(ActivitySubmission submission) {
        this.id = submission.getId();
        this.activity = submission.getActivity().getId();
        this.code = submission.getCode();
        this.status = submission.getStatus().toString();
        this.result = submission.getResult();
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

    public Result getResult() {
        return result;
    }
}
