package com.rpl.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.model.Activity;
import com.rpl.model.ActivityInputFile;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Language;
import com.rpl.model.TestType;
import com.rpl.persistence.ActivityDAO;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.service.ActionLogService;
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
	@Inject
	private ActionLogService actionLogService;
	
	
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
		actionLogService.logDeletedActivity(id);
	}
	
	public void update(Long id, String name, String description, Language language, int points, Long topic, TestType testType, String template, String input, String output, String tests) {
		activityDAO.update(id, name, description, language, points, topic, testType, template, input, output, tests);
	}
	
	public void submit(Long courseId, Activity activity) {
		Activity newActivity = activityDAO.save(activity);
		actionLogService.logNewActivity(newActivity.getId());
	}

	public Set<Long> getActivitiesDoneByCourse(Long courseId) {
		List<ActivitySubmission> submissions = activitySubmissionDAO.findSelectedByPersonAndCourse(userService.getCurrentUser().getId(), courseId);
		Set<Long> activitiesId = new HashSet<Long>();
		for (ActivitySubmission activitySubmission : submissions) {
			activitiesId.add(activitySubmission.getActivity().getId());
		}
		return activitiesId;
	}
	
	public void saveFile(Long activityId, ActivityInputFile file){
		Activity act = activityDAO.find(activityId);
		ActivityInputFile recoveredFile = activityDAO.findFileByActivityAndName(activityId, file.getFileName());
		if (recoveredFile != null){
			recoveredFile.setContent(file.getContent());
			activityDAO.save(recoveredFile);
			return;
		}
		file.setActivity(act);
		activityDAO.save(file);
	}
	
	public void deleteFile(Long fileId){
		ActivityInputFile file = activityDAO.findFile(fileId);
		activityDAO.deleteFile(file);
	}
	
	public List<ActivityInputFile> findAllFiles(Long activityId){
		return activityDAO.findFiles(activityId);
	}
}