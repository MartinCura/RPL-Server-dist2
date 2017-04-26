package com.rpl.serviceImpl;

import com.rpl.model.LoggedAction;
import com.rpl.model.Person;
import com.rpl.persistence.ActionLogDAO;
import com.rpl.service.ActionLogService;
import com.rpl.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;

@Stateless
public class ActionLogServiceImpl implements ActionLogService {
	
	@Inject
	private ActionLogDAO actionLogDAO;
	
	@Inject
	private UserService userService;
	
	private void logAction(Person p, String description) {
		actionLogDAO.save(new LoggedAction(p, description, LocalDateTime.now()));
	}

	private void logAction(String description) {
		logAction(userService.getCurrentUser(), description);
	}

	@Override
	public void logLogin(Person p) {
		this.logAction(p, "Logged in");
	}

	@Override
	public void logLogout() {
		this.logAction("Logged out");
	}

	@Override
	public void logActivitySubmission(Long activitySubmissionId) {
		this.logAction("Submitted activity with id: " + activitySubmissionId.toString());
	}

	@Override
	public void logMarkActivitySubmissionAsSelected(Long submissionId) {
		this.logAction("Marked activity submission with id: " + submissionId.toString() + " as selected");
	}

	@Override
	public void logMarkActivitySubmissionAsDefinitive(Long submissionId) {
		this.logAction("Marked activity submission with id: " + submissionId.toString() + " as definitive");
	}

	@Override
	public void logPasswordUpdate() {
		this.logAction("Updated password");
	}

	@Override
	public void logNewUserRegistered(Person p) {
		this.logAction(p, "New user registered");
	}

	@Override
	public void logDeletedActivity(Long id) {
		this.logAction("Deleted activity with id: " + id.toString());
	}

	@Override
	public void logNewCourse(Long id) {
		this.logAction("New course created with id: " + id.toString());
	}

	@Override
	public void logDeletedCourse(Long id) {
		this.logAction("Deleted course with id: " + id.toString());
	}

	@Override
	public void logJoinedCourse(Long courseId, Long personId) {
		this.logAction("Person with id: " + personId.toString() + " joined the course with id: " + courseId.toString());
	}

	@Override
	public void logLeftCourse(Long courseId, Long personId) {
		this.logAction("Person with id: " + personId.toString() + " left the course with id: " + courseId.toString());		
	}

	@Override
	public void logNewActivity(Long id) {
		this.logAction("New activity created with id: " + id.toString());
	}
}