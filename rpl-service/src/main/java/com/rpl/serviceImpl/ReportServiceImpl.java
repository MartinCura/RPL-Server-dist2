package com.rpl.serviceImpl;

import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.persistence.ActivityDAO;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.service.ReportService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class ReportServiceImpl implements ReportService {
    @Inject
    private ActivityDAO activityDAO;
    @Inject
    private ActivitySubmissionDAO activitySubmissionDAO;

    public Map<Activity, List<ActivitySubmission>> getReportByCourse(Long courseId, Long assistantId) {
        Map<Activity, List<ActivitySubmission>> submissionsByActivity = new HashMap<Activity, List<ActivitySubmission>>();

        List<Activity> activities = activityDAO.findByCourse(courseId);
        for (Activity activity : activities) {
            submissionsByActivity.put(activity, new ArrayList<ActivitySubmission>());
        }

        List<ActivitySubmission> submissions;
        if (assistantId == null) {
            submissions = activitySubmissionDAO.findSelectedByCourse(courseId);
        } else {
            submissions = activitySubmissionDAO.findSelectedByCourseAndAssistant(courseId, assistantId);
        }
        for (ActivitySubmission submission : submissions) {
            submissionsByActivity.get(submission.getActivity()).add(submission);
        }
        return submissionsByActivity;
    }
}
