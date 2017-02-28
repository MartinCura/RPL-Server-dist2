package com.rpl.service;

import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;

import java.util.List;
import java.util.Map;


public interface ReportService {

    public Map<Activity, List<ActivitySubmission>> getReportByCourse(Long courseId, Long assistantId);

}
