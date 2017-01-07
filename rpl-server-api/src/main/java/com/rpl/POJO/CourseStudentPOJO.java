package com.rpl.POJO;


import com.rpl.model.ActivitySubmission;
import com.rpl.model.Person;

import java.util.*;

public class CourseStudentPOJO {
    private Long courseId;
    private List<CourseStudentActivitiesPOJO> students;

    public CourseStudentPOJO(Long courseId, Map<Person, Set<ActivitySubmission>> submissionsByPerson) {
        this.courseId = courseId;
        students = new ArrayList<CourseStudentActivitiesPOJO>();
        for (Map.Entry<Person,  Set<ActivitySubmission>> entry : submissionsByPerson.entrySet()) {
            students.add(new CourseStudentActivitiesPOJO(entry.getKey(), entry.getValue()));
        }
    }

    public Long getCourseId() {
        return courseId;
    }

    public List<CourseStudentActivitiesPOJO> getStudents() {
        return students;
    }
}
