package com.rpl.POJO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.DatabaseState;
import com.rpl.model.Topic;

public class ReportTopicPOJO {
	private Long topicId;
	private String topicName;
	private int totalPoints;
	private List<Integer> points;

	public ReportTopicPOJO(Topic topic, List<ActivitySubmission> activitySubmissions, List<String> students) {
		this.topicId = topic.getId();
		this.topicName = topic.getName();
		this.totalPoints = topic.getActivities().stream().filter(a -> DatabaseState.ENABLED.equals(a.getState()))
				.mapToInt(a -> a.getPoints()).sum();
		this.points = new ArrayList<Integer>(Collections.nCopies(students.size(), 0));

		for (ActivitySubmission submission : activitySubmissions) {
			int pos = students.indexOf(submission.getPerson().getName());
			points.set(pos, points.get(pos) + submission.getActivity().getPoints());
		}
	}

	public Long getTopicId() {
		return topicId;
	}

	public String getTopicName() {
		return topicName;
	}

	public List<Integer> getPoints() {
		return points;
	}

	public int getTotalPoints() {
		return totalPoints;
	}
}
