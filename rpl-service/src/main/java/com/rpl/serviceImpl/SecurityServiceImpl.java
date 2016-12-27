package com.rpl.serviceImpl;

import java.util.List;

import javax.inject.Inject;

import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.exception.RplRoleException;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.SecurityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityServiceImpl implements SecurityService {

	private static final String PRIVATE_KEY = "RPL";

	@Inject
	private PersonDAO personDAO;

	public String issueToken(Person p) throws Exception {
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, PRIVATE_KEY.getBytes())
				.setClaims(p.getCredentials().toMap()).compact();
	}

	public Person authenticate(String username, String password) throws RplNotAuthorizedException {
		Person retrievedPerson = personDAO.find(username);
		return validatePassword(password, retrievedPerson);
	}

	private Person validatePassword(String password, Person retrievedPerson) throws RplNotAuthorizedException {
		if (retrievedPerson.getCredentials().getPassword().equals(password))
			return retrievedPerson;
		throw new RplNotAuthorizedException();
	}

	public Person validateToken(String token) throws RplNotAuthorizedException {
		Claims body = Jwts.parser().setSigningKey(PRIVATE_KEY.getBytes()).parseClaimsJws(token).getBody();
		String password = (String)body.get("password");
		String username = (String)body.get("username");
		Person retrievedPerson = personDAO.find(username);
		return validatePassword(password, retrievedPerson);
	}

	public void checkPermissions(List<Role> allowedRoles, Person p) throws RplRoleException {
		//TODO: MOCKED IMPL!
		//if (!allowedRoles.contains(p.getCredentials().getRole())) throw new RplRoleException();
	}

}
