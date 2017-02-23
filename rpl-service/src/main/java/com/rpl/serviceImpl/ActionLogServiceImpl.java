package com.rpl.serviceImpl;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.model.LoggedAction;
import com.rpl.model.Person;
import com.rpl.persistence.ActionLogDAO;
import com.rpl.service.ActionLogService;
import com.rpl.service.UserService;

@Stateless
public class ActionLogServiceImpl implements ActionLogService {
	
	@Inject
	private ActionLogDAO actionLogDAO;
	
	@Inject
	private UserService userService;
	
	private void logAction(Person p, String description) {
		actionLogDAO.save(new LoggedAction(p, description, new Date()));
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
	
	
	
}