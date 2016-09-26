package com.rpl.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class PingEndpoint {

	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_PLAIN)
	public String printMessage() {

		return "pong";

	}

}