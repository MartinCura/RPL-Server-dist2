package com.rpl.serviceImpl;

import java.util.List;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import com.rpl.exception.RplException;
import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.exception.RplRoleException;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.ActionLogService;
import com.rpl.service.SecurityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Stateless
public class SecurityServiceImpl implements SecurityService {

	private static final String PRIVATE_KEY = "RPL";
	
	private static final String USERNAME_PATTERN = "^[a-z0-9_-]{8,20}$";
	
	private static final Integer PASSWORD_MIN_LENGTH = 4;

	@Inject
	private PersonDAO personDAO;

	@Inject
	private ActionLogService actionLogService;
	
	public String issueToken(Person p) throws Exception {
		String generatedToken = generateToken(p);
		personDAO.updatePersonToken(p.getCredentials().getUsername(), generatedToken);
		actionLogService.logLogin(p);
		return generatedToken;
	}
	
	private String generateToken(Person p){
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, PRIVATE_KEY.getBytes())
				.setClaims(p.getCredentials().toMap()).compact();
	}

	public void logout(String username){
		personDAO.updatePersonToken(username, "");
		actionLogService.logLogout();
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
	
	private Person validateToken(String token, Person retrievedPerson) throws RplNotAuthorizedException {
		if (retrievedPerson.getCredentials().getToken().equals(token))
			return retrievedPerson;
		throw new RplNotAuthorizedException();
	}

	public Person validateToken(String token) throws RplNotAuthorizedException {
		Claims body = Jwts.parser().setSigningKey(PRIVATE_KEY.getBytes()).parseClaimsJws(token).getBody();
		String username = (String)body.get("username");
		Person retrievedPerson = personDAO.find(username);
		return validateToken(token, retrievedPerson);
	}

	@Override
	public String register(Person p) throws RplException {
		String token = generateToken(p);
		try {
			validateUsername(p.getCredentials().getUsername());
			validatePassword(p.getCredentials().getPassword());
			p.getCredentials().setToken(token);
			personDAO.save(p);
			actionLogService.logNewUserRegistered(p);
		} catch (PersistenceException e) {
			throw RplException.of("Username debe ser unico", e);
		}
		return token;
	}

	private void validatePassword(String password) throws RplException{
		if (password.length() < PASSWORD_MIN_LENGTH) throw RplException.of(1, "Password tiene que tener mas de " + PASSWORD_MIN_LENGTH + " caracteres");
	}

	private void validateUsername(String username) throws RplException {
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		if (!pattern.matcher(username).matches()){
			throw RplException.of(1, "Username tiene que tener entre 8 y 20 caracteres alfanumericos, _ o -");
		}
	}

	public void updatePassword(Long id, String password) {
		personDAO.updatePassword(id, password);
		actionLogService.logPasswordUpdate();
	}

}
