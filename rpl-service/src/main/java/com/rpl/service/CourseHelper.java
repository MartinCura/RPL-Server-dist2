package com.rpl.service;

import com.rpl.model.Activity;
import com.rpl.model.ActivityInputFile;
import com.rpl.model.Course;
import com.rpl.model.Person;
import com.rpl.model.Topic;

public class CourseHelper {
	
	public static Course getCourseByActivityId(Long activityId, Person p) {
		return p.getCoursePersons().stream().map(cp -> cp.getCourse())
				.filter(course -> getTopicByActivityId(activityId, course) != null).findFirst().get();
	}

	public static Topic getTopicByActivityId(Long activityId, Course course) {
		return course.getTopics().stream().filter(topic -> getActivityById(activityId, topic) != null).findFirst().get();
	}

	public static Activity getActivityById(Long activityId, Topic topic) {
		return topic.getActivities().stream().filter(act -> act.getId().equals(activityId)).findFirst().get();
	}
	
	public static Course getCourseByFileId(Long fileId, Person p) {
		return p.getCoursePersons().stream().map(cp -> cp.getCourse())
				.filter(course -> getTopicByFileId(fileId, course) != null).findFirst().get();
	}
	
	public static Topic getTopicByFileId(Long fileId, Course course) {
		return course.getTopics().stream().filter(topic -> getActivityByFileId(fileId, topic) != null).findFirst().get();
	}
	
	public static Activity getActivityByFileId(Long fileId, Topic topic) {
		return topic.getActivities().stream().filter(activity -> getFileByFileId(fileId, activity) != null).findFirst().get();
	}
	
	public static ActivityInputFile getFileByFileId(Long fileId, Activity activity) {
		return activity.getFiles().stream().filter(file -> file.getId().equals(fileId)).findFirst().get();
	}
}
