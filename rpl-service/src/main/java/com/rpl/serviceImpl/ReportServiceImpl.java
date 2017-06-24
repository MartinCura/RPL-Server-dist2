package com.rpl.serviceImpl;

import com.rpl.model.reports.*;
import com.rpl.persistence.ReportDAO;
import com.rpl.service.ReportService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Stateless
public class ReportServiceImpl implements ReportService {
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
