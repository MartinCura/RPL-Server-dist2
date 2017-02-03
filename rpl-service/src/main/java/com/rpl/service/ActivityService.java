package com.rpl.service;

import com.rpl.model.Activity;
import com.rpl.model.Language;
import com.rpl.model.TestType;

import java.util.List;
import java.util.Set;

public interface ActivityService {
	
	public Activity getActivityById(Long id);
	public List<Activity> getActivitiesByCourse(Long courseId);
	public List<Activity> getActivitiesByTopic(Long topicId);
	public void delete(Long id);
	public void submit(Long courseId, Activity activity);
    public void update(Long id, String name, String description, Language language, int points, Long topic, TestType testType, String template, String input, String output, String tests);
	public Set<Long> getActivitiesDoneByCourse(Long courseId);

}
