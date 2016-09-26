package com.rpl.endpoint;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class PingEndpoint {

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

}