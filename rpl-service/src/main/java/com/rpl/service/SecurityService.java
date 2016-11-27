package com.rpl.service;

import java.util.List;

import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.exception.RplRoleException;
import com.rpl.model.Person;
import com.rpl.model.Role;

public interface SecurityService {
	
	public String issueToken(Person p) throws Exception;
	
	public Person authenticate(String username, String password) throws RplNotAuthorizedException;
	
	public Person validateToken(String token) throws RplNotAuthorizedException;

	public void checkPermissions(List<Role> allowedRoles, Person p) throws RplRoleException;

}
