package com.rpl.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.model.Activity;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Language;
import com.rpl.persistence.ActivityDAO;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.service.ActivityService;
import com.rpl.service.UserService;

@Stateless
public class ActivityServiceImpl implements ActivityService {
	
	@Inject
	private ActivityDAO activityDAO;
	@Inject
	private UserService userService;
	@Inject
	private ActivitySubmissionDAO activitySubmissionDAO;
	
	
	public Activity getActivityById(Long id) {
		return activityDAO.find(id);
	}

	public List<Activity> getActivitiesByCourse(Long courseId) {
		return activityDAO.findByCourse(courseId);
	}

	public List<Activity> getActivitiesByTopic(Long topicId) {
		return activityDAO.findByTopic(topicId);
	}

	public void delete(Long id) {
		activityDAO.delete(id);
	}
	
	public void update(Long id, Language lang, int points, String name, String description, String template){
		activityDAO.update(id, lang, points, name, description, template);
	}
	
	public void submit(Long courseId, Activity activity) {
		activityDAO.save(activity);
	}

	public Set<Long> getActivitiesDoneByCourse(Long courseId) {
		List<ActivitySubmission> submissions = activitySubmissionDAO.findSelectedByPersonAndCourse(userService.getCurrentUser().getId(), courseId);
		Set<Long> activitiesId = new HashSet<Long>();
		for (ActivitySubmission activitySubmission : submissions) {
			activitiesId.add(activitySubmission.getActivity().getId());
		}
		return activitiesId;
	}
}