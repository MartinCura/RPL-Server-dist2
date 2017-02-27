package com.rpl.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import com.rpl.exception.RplException;
import com.rpl.exception.RplNotAuthorizedException;
import com.rpl.model.MessageCodes;
import com.rpl.model.Person;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.ActionLogService;
import com.rpl.service.SecurityService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Stateless
public class SecurityServiceImpl implements SecurityService {

	private static final String PRIVATE_KEY = "RPL";

	private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{8,20}$";

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

	private String generateToken(Person p) {
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, PRIVATE_KEY.getBytes())
				.setClaims(p.getCredentials().toMap()).compact();
	}
	
	private String encodePassword(String password) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("password", password);
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, PRIVATE_KEY.getBytes())
				.setClaims(claims).compact();
	}

	public void logout(String username) {
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
		String username = (String) body.get("username");
		Person retrievedPerson = personDAO.find(username);
		return validateToken(token, retrievedPerson);
	}

	@Override
	public String register(Person p) throws RplException {
		String originalPassword = p.getCredentials().getPassword();
		String encodedPassword = encodePassword(originalPassword);
		p.getCredentials().setPassword(encodedPassword);
		String token = generateToken(p);
		try {
			validateUsername(p.getCredentials().getUsername());
			validatePassword(originalPassword);
			p.getCredentials().setToken(token);
			Person savedPerson = personDAO.save(p);
			actionLogService.logNewUserRegistered(savedPerson);
		} catch (PersistenceException e) {
			throw RplException.of("Username debe ser unico", e);
		}
		return token;
	}

	private void validatePassword(String password) throws RplException {
		if (password.length() < PASSWORD_MIN_LENGTH)
			throw RplException.of(MessageCodes.ERROR_PASSWORD_MIN_LENGTH,
					"Password tiene que tener mas de " + PASSWORD_MIN_LENGTH + " caracteres");
	}

	private void validateUsername(String username) throws RplException {
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		if (!pattern.matcher(username).matches()) {
			throw RplException.of(MessageCodes.ERROR_USERNAME_ALREADY_EXISTS,
					"Username tiene que tener entre 8 y 20 caracteres alfanumericos, _ o -");
		}
	}

	public void updatePassword(Long id, String password) {
		String encodedPassword = encodePassword(password);
		personDAO.updatePassword(id, encodedPassword);
		actionLogService.logPasswordUpdate();
	}

}
