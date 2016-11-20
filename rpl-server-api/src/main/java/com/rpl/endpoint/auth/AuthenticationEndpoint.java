package com.rpl.endpoint.auth;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.model.Credentials;
import com.rpl.model.Person;
import com.rpl.model.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/authentication")
public class AuthenticationEndpoint {

	private static final String PRIVATE_KEY = "RPL";

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(String username, String password) {

		try {

			Person p = authenticate(username, password);

			String token = issueToken(p);
			
			//Jwts.parser().setSigningKey(PRIVATE_KEY.getBytes()).parseClaimsJws(token).getBody().get("password");

			return Response.ok(token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	private Person authenticate(String username, String password) throws Exception {
		// TODO Must be part of a service
		Person p = new Person();
		Credentials cred = new Credentials();
		p.setCredentials(cred);
		cred.setPassword("test");
		cred.setRole(Role.ADMIN);
		cred.setUsername("test");
		return p;
	}

	private String issueToken(Person p) throws Exception {
		return Jwts.builder().signWith(SignatureAlgorithm.HS512, PRIVATE_KEY.getBytes()).
						setClaims(p.getCredentials().toMap()).compact();
	}

}
