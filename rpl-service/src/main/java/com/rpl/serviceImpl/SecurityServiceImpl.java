package com.rpl.serviceImpl;

import java.util.List;

import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.exception.RplRoleException;
import com.rpl.model.Credentials;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.service.SecurityService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityServiceImpl implements SecurityService {

	private static final String PRIVATE_KEY = "RPL";

	public String issueToken(Person p) throws Exception {
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, PRIVATE_KEY.getBytes())
				.setClaims(p.getCredentials().toMap()).compact();
	}

	public Person authenticate(String username, String password) throws RplNotAuthorizedException {
		// TODO queries the db and checks if the
		// password matches
		Person p = new Person();
		Credentials cred = new Credentials();
		p.setCredentials(cred);
		cred.setPassword("test");
		cred.setRole(Role.ADMIN);
		cred.setUsername("test");
		return p;
	}

	public Person validateToken(String token) throws RplNotAuthorizedException {
		//TODO MOCK
		Object pass = Jwts.parser().setSigningKey(PRIVATE_KEY.getBytes()).parseClaimsJws(token).getBody()
				.get("password");
		return new Person();
	}

	public void checkPermissions(List<Role> allowedRoles, Person p) throws RplRoleException{
		// Check if the user contains one of the allowed roles
		// Throw an Exception if the user has not permission to execute the
		// method
		//if (!allowedRoles.contains(p.getCredentials().getRole())) throw new RplRoleException();
		//TODO MOCK
	}

}
