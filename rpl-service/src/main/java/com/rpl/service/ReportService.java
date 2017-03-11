package com.rpl.service;

import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Topic;

import java.util.List;
import java.util.Map;


public interface ReportService {

    public Map<Activity, List<ActivitySubmission>> getActivityReportByCourse(Long courseId, Long assistantId);
    public Map<Topic, List<ActivitySubmission>> getTopicReportByCourse(Long courseId, Long assistantId);

}
