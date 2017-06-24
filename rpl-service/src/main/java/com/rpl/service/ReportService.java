package com.rpl.service;

import com.rpl.model.reports.*;

import java.util.List;


public interface ReportService {

    public List<Report1> getReport1(Long courseId, Long personId, Long topicId);
    public List<Report2> getReport2(Long topicId);
    public List<Report3> getReport3(Long topicId);
    public List<Report5> getReport5(Long topicId);
    public List<Report6> getReport6(Long topicId, Long personId);
    public List<ReportRanking> getReportRanking(Long courseId, String dateStr);

}
