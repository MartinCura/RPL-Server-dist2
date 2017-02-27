package com.rpl.POJO;


import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.CoursePerson;

import java.util.*;

public class ReportCoursePOJO {
    private Long courseId;
    private List<String> students;
    private List<ReportActivityPOJO> activities;

    public ReportCoursePOJO(Long courseId, List<CoursePerson> persons, Map<Activity, List<ActivitySubmission>> submissionsByActivity) {
        this.courseId = courseId;
        this.students = new ArrayList<String>();
        for (CoursePerson p : persons) {
            students.add(p.getPerson().getName());
        }

        activities = new ArrayList<ReportActivityPOJO>();
        for (Map.Entry<Activity,  List<ActivitySubmission>> entry : submissionsByActivity.entrySet()) {
            activities.add(new ReportActivityPOJO(entry.getKey(), entry.getValue()));
        }
    }

    public Long getCourseId() {
        return courseId;
    }

    public List<String> getStudents() {
        return students;
    }

    public List<ReportActivityPOJO> getActivities() {
        return activities;
    }
}
