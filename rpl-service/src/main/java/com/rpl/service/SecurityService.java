package com.rpl.service;

import java.util.List;

import com.rpl.exception.RplException;
import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.exception.RplRoleException;
import com.rpl.model.Person;
import com.rpl.model.Role;

public interface SecurityService {
	
	public String issueToken(Person p) throws Exception;
	
	public Person authenticate(String username, String password) throws RplNotAuthorizedException;
	
	public Person validateToken(String token) throws RplNotAuthorizedException;

	public void checkPermissions(List<Role> allowedRoles, Person p) throws RplRoleException;
	
	public void logout(String username);

	public String register(Person p) throws RplException;

	public void updatePassword(Long id, String password);

}
