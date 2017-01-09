package com.rpl.endpoint.auth;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.CredentialsPOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.RegisterPOJO;
import com.rpl.exception.RplException;
import com.rpl.model.Credentials;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.service.SecurityService;

@Path("/authentication")
public class AuthenticationEndpoint {

	@Inject
	private SecurityService securityService;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response authenticateUser(CredentialsPOJO c) {

		try {
			Person p = securityService.authenticate(c.getUsername(), c.getPassword());

			String token = securityService.issueToken(p);

			return Response.ok(token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {

		try {
			// TODO
			securityService.logout("MOCK");

			return Response.ok().build();

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(RegisterPOJO regPojo) {

		try {

			Person p = new Person();
			p.setMail(regPojo.getMail());
			p.setName(regPojo.getName());
			Credentials c = new Credentials();
			c.setUsername(regPojo.getUsername());
			c.setPassword(regPojo.getPassword());
			c.setRole(Role.USER);
			p.setCredentials(c);

			String token = securityService.register(p);

			return Response.ok(token).build();

		} catch (RplException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(MessagePOJO.of(e.getMsg())).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(MessagePOJO.of(e.getMessage())).build();
		}
	}

}
