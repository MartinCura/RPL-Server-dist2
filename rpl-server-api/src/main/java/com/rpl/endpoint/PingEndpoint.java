package com.rpl.endpoint;

import java.util.HashMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.annotation.Secured;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.service.EntityManagerTest;
import com.rpl.service.UserService;

@Path("/")
public class PingEndpoint {

	@Inject
	private EntityManagerTest entityManagerTest;
	
	@Inject
	private UserService userService;
	
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String ping() {

		return "pong";

	}
	
	@GET
	@Path("/pingJson")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> pingJson() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Response", "pong");
		
		return map;

	}
	
	@GET
	@Path("/pingJsonStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pingJsonStatus() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Response", "pong");
		
		return Response.status(200).entity(map).build();

	}
	
	@GET
	@Path("/testDB")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testDB() {
		
		return Response.status(200).entity(entityManagerTest.find()).build();

	}
	
	@POST
	@Path("/testDBSave")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testDB2() {
		
		return Response.status(200).entity(entityManagerTest.save()).build();

	}
	
	@GET
	@Path("/currentUserTest/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testCurrentUser(@PathParam("name") String name) {
		
		Person p = new Person();
		p.setName(name);
		userService.setCurrentUser(p);
		Person p2 = userService.getCurrentUser();
		
		return Response.status(200).entity(p2).build();
	}
	
	@GET
	@Secured
	@Path("/securityTesting")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testsecurity() {
		return Response.status(200).entity(userService.getCurrentUser()).build();
	}
	
	
	
	
}