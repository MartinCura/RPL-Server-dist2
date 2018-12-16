package com.rpl.service;

import com.rpl.model.reports.*;

import java.util.List;


public interface ReportService {

    List<Report1> getReport1(Long courseId, Long personId, Long topicId);
    List<Report2> getReport2(Long topicId);
    List<Report3> getReport3(Long topicId);
    List<Report3> getReport3ByAssistant(Long topicId, Long assistantId);
    List<Report4> getReport4(Long courseId);
    List<Report4> getReport4ByAssistant(Long courseId, Long assistantId);
    List<Report5> getReport5(Long topicId);
    List<Report5> getReport5ByAssistant(Long topicId, Long assistantId);
    List<Report6> getReport6(Long topicId, Long personId);
    List<Report7> getReport7(Long courseId, String dateStr);
    List<Report7> getReport7ByAssistant(Long courseId, String dateStr, Long assistantId);

}
