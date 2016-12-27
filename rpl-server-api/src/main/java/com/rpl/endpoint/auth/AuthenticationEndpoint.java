package com.rpl.endpoint.auth;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.model.Credentials;
import com.rpl.model.Person;
import com.rpl.service.SecurityService;

@Path("/authentication")
public class AuthenticationEndpoint {

	@Inject
	private SecurityService securityService;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response authenticateUser(Credentials c) {

		try {

			Person p = securityService.authenticate(c.getUsername(), c.getPassword());

			String token = securityService.issueToken(p);
			
			return Response.ok(token).build();

		} catch (Exception e) {
			//TODO DO NOT LOSE THE EXCEPTION
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

}
