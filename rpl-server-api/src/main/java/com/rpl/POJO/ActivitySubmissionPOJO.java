package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;

public class ActivitySubmissionPOJO {
    private Long activity;
    private String code;
    private String status;
    private String result;

    public ActivitySubmissionPOJO(ActivitySubmission submission) {
        this.activity = submission.getActivity().getId();
        this.code = submission.getCode();
        this.status = submission.getStatus().toString();
        if (submission.getResult() != null)
            this.result = submission.getResult().getStatus().getResult();
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

    public String getResult() {
        return result;
    }
}
