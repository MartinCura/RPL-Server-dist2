package com.rpl.POJO;


import com.rpl.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReportCourseTopicsPOJO {
    private Long courseId;
    private String courseName;
    private List<String> students;
    private List<ReportTopicPOJO> topics;

    public ReportCourseTopicsPOJO(Course course, List<CoursePerson> persons, List<Topic> topics, Map<Topic, List<ActivitySubmission>> submissionsByTopic) {
        this.courseId = course.getId();
        this.courseName = course.getName();
        this.students = new ArrayList<String>();
        for (CoursePerson p : persons) {
            students.add(p.getPerson().getName());
        }

        this.topics = new ArrayList<ReportTopicPOJO>();
        submissionsByTopic.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e1.getKey().getId(), e2.getKey().getId()))
                .forEach((entry)-> this.topics.add(new ReportTopicPOJO(entry.getKey(), entry.getValue(), students)));

    }

    public Long getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public List<String> getStudents() {
        return students;
    }

    public List<ReportTopicPOJO> getTopics() {
        return topics;
    }
}
