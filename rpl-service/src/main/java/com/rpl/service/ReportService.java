package com.rpl.service;

import com.rpl.model.reports.*;

import java.util.List;


public interface ReportService {

    public List<Report1> getReport1(Long courseId, Long personId, Long topicId);
    public List<Report2> getReport2(Long topicId);
    public List<Report3> getReport3(Long topicId);
    public List<Report3> getReport3ByAssistant(Long topicId, Long assistantId);
    public List<Report5> getReport5(Long topicId);
    public List<Report5> getReport5ByAssistant(Long topicId, Long assistantId);
    public List<Report6> getReport6(Long topicId, Long personId);
    public List<Report7> getReport7(Long courseId, String dateStr);
    public List<Report7> getReport7ByAssistant(Long courseId, String dateStr, Long assistantId);

}
