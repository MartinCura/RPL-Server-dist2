package com.rpl.service;

import com.rpl.model.Person;

public interface ActionLogService {

    void logLogin(Person p);

    void logLogout();

    void logPasswordUpdate();

    void logActivitySubmission(Long activitySubmissionId);

    void logMarkActivitySubmissionAsSelected(Long submissionId);

    void logMarkActivitySubmissionAsDefinitive(Long submissionId);

    void logNewUserRegistered(Person p);

    void logDeletedActivity(Long id);

    void logNewCourse(Long id);

    void logDeletedCourse(Long id);

    void logJoinedCourse(Long courseId, Long personId);

    void logLeftCourse(Long courseId, Long personId);

    void logNewActivity(Long id);

}
