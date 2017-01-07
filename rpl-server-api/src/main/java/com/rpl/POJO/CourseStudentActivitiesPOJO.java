package com.rpl.POJO;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CourseStudentActivitiesPOJO {
    private PersonPOJO person;
    private List<ActivitySubmissionPOJO> activities;

    public CourseStudentActivitiesPOJO(Person person, Set<ActivitySubmission> submissions) {
        this.person = new PersonPOJO(person);
        activities = new ArrayList<ActivitySubmissionPOJO>();
        for(ActivitySubmission submission : submissions) {
            activities.add(new ActivitySubmissionPOJO(submission));
        }
    }

    public PersonPOJO getPerson() {
        return person;
    }

    public List<ActivitySubmissionPOJO> getActivities() {
        return activities;
    }
}
