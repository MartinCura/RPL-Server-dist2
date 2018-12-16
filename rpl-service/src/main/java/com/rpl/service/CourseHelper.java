package com.rpl.service;

import java.util.Optional;

import com.rpl.model.*;

public class CourseHelper {
	
	public static Optional<Course> getCourseByActivityId(Long activityId, Person p) {
		return p.getCoursePersons().stream().map(CoursePerson::getCourse)
				.filter(course -> getTopicByActivityId(activityId, course).isPresent()).findFirst();
	}

	public static Optional<Topic> getTopicByActivityId(Long activityId, Course course) {
		return course.getTopics().stream().filter(topic -> getActivityById(activityId, topic).isPresent()).findFirst();
	}
	

	public static Optional<Activity> getActivityById(Long activityId, Topic topic) {
		return topic.getActivities().stream().filter(act -> act.getId().equals(activityId)).findFirst();
	}
	
	public static Optional<Course> getCourseByFileId(Long fileId, Person p) {
		return p.getCoursePersons().stream().map(CoursePerson::getCourse)
				.filter(course -> getTopicByFileId(fileId, course).isPresent()).findFirst();
	}
	
	public static Optional<Course> getCourseByTopicId(Long topicId, Person p) {
		return p.getCoursePersons().stream().map(CoursePerson::getCourse)
				.filter(course -> getTopicById(topicId, course).isPresent()).findFirst();
	}
	
	private static Optional<Topic> getTopicById(Long topicId, Course course) {
		return course.getTopics().stream().filter(topic -> topic.getId().equals(topicId)).findFirst();
	}

	public static Optional<Topic> getTopicByFileId(Long fileId, Course course) {
		return course.getTopics().stream().filter(topic -> getActivityByFileId(fileId, topic).isPresent()).findFirst();
	}
	
	public static Optional<Activity> getActivityByFileId(Long fileId, Topic topic) {
		return topic.getActivities().stream().filter(activity -> getFileByFileId(fileId, activity).isPresent()).findFirst();
	}
	
	public static Optional<ActivityInputFile> getFileByFileId(Long fileId, Activity activity) {
		return activity.getFiles().stream().filter(file -> file.getId().equals(fileId)).findFirst();
	}

	public static Optional<Course> getCourseByImageFileId(Long fileId, Person p) {
		return p.getCoursePersons().stream().map(CoursePerson::getCourse)
				.filter(course -> course.getCourseImage().getId().equals(fileId)).findFirst();
	}

}
