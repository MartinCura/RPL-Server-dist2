package com.rpl.service;

import com.rpl.exception.RplException;
import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.model.Person;

public interface SecurityService {
	
	String issueToken(Person p);
	
	Person authenticate(String username, String password) throws RplNotAuthorizedException;
	
	Person validateToken(String token) throws RplNotAuthorizedException;

	void logout(String username);

	String register(Person p) throws RplException;

	void updatePassword(Long id, String password);

}
