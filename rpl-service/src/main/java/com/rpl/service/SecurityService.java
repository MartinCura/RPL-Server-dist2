package com.rpl.service;

import com.rpl.exception.RplException;
import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.model.Person;

public interface SecurityService {
	
	public String issueToken(Person p) throws Exception;
	
	public Person authenticate(String username, String password) throws RplNotAuthorizedException;
	
	public Person validateToken(String token) throws RplNotAuthorizedException;

	public void logout(String username);

	public String register(Person p) throws RplException;

	public void updatePassword(Long id, String password);

}
