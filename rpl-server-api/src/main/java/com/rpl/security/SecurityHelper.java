package com.rpl.security;

import java.util.List;
import java.util.stream.Collectors;

import com.rpl.exception.RplItemDoesNotBelongToPersonException;
import com.rpl.exception.RplRoleException;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.model.RoleCourse;
import com.rpl.service.CourseHelper;
import com.rpl.service.util.Utils;

public class SecurityHelper {

	public static RoleCourse findRoleOnCourse(Long courseId, Person p) {
		return p.getCoursePersons().stream().filter(cp -> cp.getCourse().getId().equals(courseId))
				.map(cp -> cp.getRole()).findFirst().get();
	}

	public static void checkSubmissionBelongsToPerson(ActivitySubmission activitySubmission, Person p) throws RplItemDoesNotBelongToPersonException{
		if (!activitySubmission.getPerson().getId().equals(p.getId())) throw new RplItemDoesNotBelongToPersonException();
	}
	
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
		Course c = CourseHelper.getCourseByActivityId(activityId, p);
		if (c != null) {
			checkPermissions(c.getId(), allowedRoles, p);
		} else {
			throw new RplRoleException();
		}
	}
	
	public static void checkPermissionsByTopicId(Long topicId, List<RoleCourse> allowedRoles, Person p)
			throws RplRoleException {
		Course c = CourseHelper.getCourseByTopicId(topicId, p);
		if (c != null) {
			checkPermissions(c.getId(), allowedRoles, p);
		} else {
			throw new RplRoleException();
		}
	}

	public static void checkPermissionsByFileId(Long fileId, List<RoleCourse> allowedRoles, Person p)
			throws RplRoleException {
		Course c = CourseHelper.getCourseByFileId(fileId, p);
		if (c != null) {
			checkPermissions(c.getId(), allowedRoles, p);
		} else {
			throw new RplRoleException();
		}
	}

	public static void checkPermissions(Long courseId, RoleCourse allowedRole, Person p) throws RplRoleException {
		// TODO: MOCKED IMPL!
		SecurityHelper.checkPermissions(courseId, Utils.listOf(allowedRole), p);
	}

}
