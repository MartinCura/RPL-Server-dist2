package com.rpl.POJO;


import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.CoursePerson;
import com.rpl.service.util.Utils;

import java.util.*;

public class ReportCourseActivitiesPOJO {
    private Long courseId;
    private String courseName;
    private List<String> students;
    private List<ReportActivityPOJO> activities;

    public ReportCourseActivitiesPOJO(Course course, List<CoursePerson> persons, Map<Activity, List<ActivitySubmission>> submissionsByActivity) {
        this.courseId = course.getId();
        this.courseName = course.getName();
        this.students = new ArrayList<String>();
        for (CoursePerson p : persons) {
        	students.add(Utils.getCompleteName(p.getPerson()));
        }

        activities = new ArrayList<ReportActivityPOJO>();
        submissionsByActivity.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e1.getKey().getId(), e2.getKey().getId()))
                .forEach((entry)-> activities.add(new ReportActivityPOJO(entry.getKey(), entry.getValue())));
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

    public List<ReportActivityPOJO> getActivities() {
        return activities;
    }
}
