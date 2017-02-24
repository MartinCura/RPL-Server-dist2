package com.rpl.security;

import java.util.List;
import java.util.stream.Collectors;

import com.rpl.exception.RplRoleException;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.model.RoleCourse;

public class SecurityHelper {

	public static void checkPermissions(List<Role> allowedRoles, Person p) throws RplRoleException {
		// TODO: MOCKED IMPL!
		// if (!allowedRoles.contains(p.getCredentials().getRole())) throw new
		// RplRoleException();
	}

	public static void checkPermissions(Long courseId, List<RoleCourse> allowedRoles, Person p) throws RplRoleException {
		// TODO: MOCKED IMPL!
		List<RoleCourse> personRoles = p.getCoursePersons().stream().filter(cp -> cp.getCourse().getId().equals(courseId))
				.map(cp -> cp.getRole()).collect(Collectors.toList());
		for (RoleCourse allowedRole : allowedRoles) {
			if (personRoles.contains(allowedRole)) return;
		}
		throw new RplRoleException();
	}

}
