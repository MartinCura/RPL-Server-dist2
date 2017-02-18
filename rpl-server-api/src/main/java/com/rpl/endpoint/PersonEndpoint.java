package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.PersonInfoPOJO;
import com.rpl.POJO.PersonPOJO;
import com.rpl.annotation.Secured;
import com.rpl.model.Person;
import com.rpl.service.PersonService;
import com.rpl.service.UserService;

@Secured
@Path("/persons")
public class PersonEndpoint {
	
	@Inject
	private PersonService personService;
	
	@Inject
	private UserService userService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersons() {

		List<Person> persons = personService.getPersons();
		List<PersonPOJO> personPOJOS = new ArrayList<PersonPOJO>();
		for (Person p : persons) {
			personPOJOS.add(new PersonPOJO(p));
		}
		return Response.status(200).entity(personPOJOS).build();

	}

	@GET
	@Path("/information")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersonInfo() {

		Person p = personService.getPersonById(userService.getCurrentUser().getId());
		return Response.status(200).entity(new PersonInfoPOJO(p)).build();

	}
	
	@PUT
	@Path("/information")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePersonInfoById(PersonInfoPOJO pojo) {
		personService.updatePersonInfo(userService.getCurrentUser().getId(), pojo.getName(), pojo.getMail());
		return Response.ok().build();
	}
}
