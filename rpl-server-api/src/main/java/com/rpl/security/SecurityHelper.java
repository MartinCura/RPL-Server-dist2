package com.rpl.security;

import java.util.List;
import java.util.stream.Collectors;

import com.rpl.exception.RplRoleException;
import com.rpl.model.Activity;
import com.rpl.model.Course;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.model.RoleCourse;
import com.rpl.model.Topic;
import com.rpl.service.util.Utils;

public class SecurityHelper {

	public static void checkPermissions(List<Role> allowedRoles, Person p) throws RplRoleException {
		// TODO: MOCKED IMPL!
		// if (!allowedRoles.contains(p.getCredentials().getRole())) throw new
		// RplRoleException();
	}

	public static void checkPermissions(Long courseId, List<RoleCourse> allowedRoles, Person p)
			throws RplRoleException {
		// TODO: MOCKED IMPL!
		List<RoleCourse> personRoles = p.getCoursePersons().stream()
				.filter(cp -> cp.getCourse().getId().equals(courseId)).map(cp -> cp.getRole())
				.collect(Collectors.toList());
		if (personRoles != null) {
			for (RoleCourse allowedRole : allowedRoles) {
				if (personRoles.contains(allowedRole))
					return;
			}
		}
		throw new RplRoleException();
	}

	public static void checkPermissionsByActivityId(Long activityId, List<RoleCourse> allowedRoles, Person p)
			throws RplRoleException {
		Course c = getCourseByActivityId(activityId, p);
		if (c != null){
			checkPermissions(c.getId(), allowedRoles, p);
		} else {
			throw new RplRoleException();
		}
		
	}

	private static Course getCourseByActivityId(Long activityId, Person p) {
		return p.getCoursePersons().stream().map(cp -> cp.getCourse())
				.filter(course -> getTopicByActivityId(activityId, course) != null).findFirst().get();
	}

	private static Topic getTopicByActivityId(Long activityId, Course course) {
		return course.getTopics().stream().filter(topic -> getActivityById(activityId, topic) != null).findFirst().get();
	}

	private static Activity getActivityById(Long activityId, Topic topic) {
		return topic.getActivities().stream().filter(act -> act.getId().equals(activityId)).findFirst().get();
	}

	public static void checkPermissions(Long courseId, RoleCourse allowedRole, Person p) throws RplRoleException {
		// TODO: MOCKED IMPL!
		SecurityHelper.checkPermissions(courseId, Utils.listOf(allowedRole), p);
	}

}
