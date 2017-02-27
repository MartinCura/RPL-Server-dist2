package com.rpl.POJO;

import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;

import java.util.ArrayList;
import java.util.List;

public class ReportActivityPOJO {
    private Long activityId;
    private String activityName;
    private List<ReportSubmissionPOJO> submissions;

    public ReportActivityPOJO(Activity activity, List<ActivitySubmission> activitySubmissions) {
        this.activityId = activity.getId();
        this.activityName = activity.getName();
        this.submissions = new ArrayList<ReportSubmissionPOJO>();
        for (ActivitySubmission submission : activitySubmissions) {
            this.submissions.add(new ReportSubmissionPOJO(submission));
        }
    }

    public Long getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public List<ReportSubmissionPOJO> getSubmissions() {
        return submissions;
    }
}
