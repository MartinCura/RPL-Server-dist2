package com.rpl.service;

import java.util.List;
import java.util.Set;

import com.rpl.exception.RplException;
import com.rpl.model.Activity;
import com.rpl.model.ActivityInputFile;
import com.rpl.model.Language;
import com.rpl.model.TestType;

public interface ActivityService {

    Activity getActivityById(Long id);
    List<Activity> getActivitiesByCourseEnabledAndDisabled(Long courseId);
    List<Activity> getActivitiesByTopic(Long topicId);
    void delete(Long id);
    void submit(Long courseId, Activity activity);
    void update(Long id, String name, String description, Language language, int points, Long topic, TestType testType, String template, String input, String output, String tests);
    Set<Long> getActivitiesSelectedByCourse(Long courseId);
    Set<Long> getActivitiesDefinitiveByCourse(Long courseId);
    void saveFile(Long activityId, ActivityInputFile file) throws RplException;
    void deleteFile(Long fileId);
    List<ActivityInputFile> findAllFiles(Long activityId);
    void hide(Long activityId);
    void unhide(Long activityId);

}
