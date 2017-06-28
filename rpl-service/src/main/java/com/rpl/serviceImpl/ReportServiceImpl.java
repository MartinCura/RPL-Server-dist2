package com.rpl.serviceImpl;

import com.rpl.model.reports.*;
import com.rpl.persistence.ReportDAO;
import com.rpl.service.ReportService;
import com.rpl.service.util.Utils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Stateless
public class ReportServiceImpl implements ReportService {
    private static final int CALENDAR_TIME = 6;
    @Inject
    private ReportDAO reportDAO;

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

    public List<Report3> getReport3ByAssistant(Long topicId, Long assistantId) {
        return reportDAO.getReport3ByAssistant(topicId, assistantId);
    }

    public List<Report4> getReport4(Long courseId, String dateStr) {
        Date dateFrom = Utils.stringToDate(dateStr);
        Date dateTo = Utils.addMonths(dateFrom, CALENDAR_TIME);
        return reportDAO.getReport4(courseId, dateFrom, dateTo);
    }

    public List<Report4> getReport4ByAssistant(Long courseId, String dateStr, Long assistantId) {
        Date dateFrom = Utils.stringToDate(dateStr);
        Date dateTo = Utils.addMonths(dateFrom, CALENDAR_TIME);
        return reportDAO.getReport4ByAssistant(courseId, dateFrom, dateTo, assistantId);
    }

    public List<Report5> getReport5(Long topicId) {
        return reportDAO.getReport5(topicId);
    }

    public List<Report5> getReport5ByAssistant(Long topicId, Long assistantId) {
        return reportDAO.getReport5ByAssistant(topicId, assistantId);
    }

    public List<Report6> getReport6(Long topicId, Long personId) {
        return reportDAO.getReport6(topicId, personId);
    }

    public List<Report7> getReport7(Long courseId, String dateStr) {
        Date date = Utils.stringToDate(dateStr);
        return reportDAO.getReport7(courseId, date);
    }

    public List<Report7> getReport7ByAssistant(Long courseId, String dateStr, Long assistantId) {
        Date date = Utils.stringToDate(dateStr);
        return reportDAO.getReport7ByAssistant(courseId, date, assistantId);
    }
}
