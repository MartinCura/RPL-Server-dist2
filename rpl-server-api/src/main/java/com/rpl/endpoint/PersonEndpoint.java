package com.rpl.endpoint;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.PersonInfoPOJO;
import com.rpl.annotation.Secured;
import com.rpl.model.Person;
import com.rpl.service.PersonService;

@Secured
@Path("/persons")
public class PersonEndpoint {
	
	@Inject
	private PersonService personService;

	@GET
	@Path("/{id}/information")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersonInfoById(@PathParam("id") Long id) {

		Person p = personService.getPersonById(id);
		return Response.status(200).entity(new PersonInfoPOJO(p)).build();

	}
	
	@PUT
	@Path("/{id}/information")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePersonInfoById(@PathParam("id") Long id, PersonInfoPOJO pojo) {
		personService.updatePersonInfo(id, pojo.getName(), pojo.getMail());
		return Response.ok().build();
	}

}
