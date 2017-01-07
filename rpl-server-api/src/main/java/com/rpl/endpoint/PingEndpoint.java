package com.rpl.endpoint;

import java.util.HashMap;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.service.EntityManagerTest;

@Path("/")
public class PingEndpoint {

	@Inject
	private EntityManagerTest entityManagerTest;
	
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
}