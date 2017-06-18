package com.rpl.service;

import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Topic;
import com.rpl.model.reports.Report1;
import com.rpl.model.reports.Report2;
import com.rpl.model.reports.Report5;
import com.rpl.model.reports.Report6;

import java.util.List;
import java.util.Map;


public interface ReportService {

    public Map<Activity, List<ActivitySubmission>> getActivityReportByCourse(Long courseId, Long assistantId);
    public Map<Topic, List<ActivitySubmission>> getTopicReportByCourse(Long courseId, Long assistantId);

    public List<Report1> getReport1(Long personId, Long topicId);
    public List<Report2> getReport2(Long topicId);
    public List<Report5> getReport5(Long topicId);
    public List<Report6> getReport6(Long topicId, Long personId);
}
