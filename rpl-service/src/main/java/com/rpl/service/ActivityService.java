package com.rpl.service;

import com.rpl.model.Activity;
import com.rpl.model.Language;

import java.util.List;
import java.util.Set;

public interface ActivityService {
	
	public Activity getActivityById(Long id);
	public List<Activity> getActivitiesByCourse(Long courseId);
	public List<Activity> getActivitiesByTopic(Long topicId);
	public void delete(Long id);
	public void submit(Long courseId, Activity activity);
	public void update(Long id, Language lang, int points, String name, String description, String template);
	public Set<Long> getActivitiesDoneByCourse(Long courseId);

}
