package com.rpl.serviceImpl;

import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Topic;
import com.rpl.model.reports.*;
import com.rpl.persistence.ActivityDAO;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.persistence.ReportDAO;
import com.rpl.persistence.TopicDAO;
import com.rpl.service.ReportService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

@Stateless
public class ReportServiceImpl implements ReportService {
    @Inject
    private ActivityDAO activityDAO;
    @Inject
    private TopicDAO topicDAO;
    @Inject
    private ActivitySubmissionDAO activitySubmissionDAO;
    @Inject
    private ReportDAO reportDAO;

    public Map<Activity, List<ActivitySubmission>> getActivityReportByCourse(Long courseId, Long assistantId) {
        Map<Activity, List<ActivitySubmission>> submissionsByActivity = new HashMap<Activity, List<ActivitySubmission>>();

        List<Activity> activities = activityDAO.findByCourseEnabledOnly(courseId);
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

    public Map<Topic, List<ActivitySubmission>> getTopicReportByCourse(Long courseId, Long assistantId) {
        Map<Topic, List<ActivitySubmission>> submissionsByTopic = new HashMap<Topic, List<ActivitySubmission>>();

        List<Topic> topics = topicDAO.findByCourseIdEnabledOnly(courseId);
        for (Topic topic : topics) {
            submissionsByTopic.put(topic, new ArrayList<ActivitySubmission>());
        }

        List<ActivitySubmission> submissions;
        if (assistantId == null) {
            submissions = activitySubmissionDAO.findSelectedByCourse(courseId);
        } else {
            submissions = activitySubmissionDAO.findSelectedByCourseAndAssistant(courseId, assistantId);
        }
        for (ActivitySubmission submission : submissions) {
            submissionsByTopic.get(submission.getActivity().getTopic()).add(submission);
        }
        return submissionsByTopic;
    }

    public List<Report1> getReport1(Long courseId, Long personId, Long topicId) {
        if (topicId == null) {
            return reportDAO.getReport1ByCourse(personId, courseId);
        }
        return reportDAO.getReport1ByTopic(personId, topicId);
    }

    public List<Report2> getReport2(Long topicId) {
        return reportDAO.getReport2(topicId);
    }

    public List<Report3> getReport3(Long topicId) {
        return reportDAO.getReport3(topicId);
    }

    public List<Report5> getReport5(Long topicId) {
        return reportDAO.getReport5(topicId);
    }

    public List<Report6> getReport6(Long topicId, Long personId) {
        return reportDAO.getReport6(topicId, personId);
    }

    public List<ReportRanking> getReportRanking(Long courseId, String dateStr) {
        Date date;
        if (dateStr != null) {
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e) {
                date = new Date();
            }
        } else {
            date = new Date();
        }
        return reportDAO.getReportRanking(courseId, date);
    }
}
