package com.rpl.endpoint;

import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.PersonInfoPOJO;
import com.rpl.POJO.PersonPOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplException;
import com.rpl.model.MessageCodes;
import com.rpl.model.Person;
import com.rpl.model.PersonImage;
import com.rpl.model.Role;
import com.rpl.service.PersonService;
import com.rpl.service.UserService;
import com.rpl.service.util.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/persons")
public class PersonEndpoint {

	@Inject
	private PersonService personService;

	@Inject
	private UserService userService;

	@Secured(Role.ADMIN)
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

	@Secured
	@GET
	@Path("/information")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersonInfo() {

		Person p = personService.getPersonById(userService.getCurrentUser().getId());
		return Response.status(200).entity(new PersonInfoPOJO(p)).build();

	}

	@GET
	@Path("/{id}/image")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("image/*")
	public Response getPersonImage(@PathParam("id") Long personId) {
		Person p = personService.getPersonById(personId);
		return Response.status(200).entity(p.getPersonImage() != null ? p.getPersonImage().getContent() : null).build();

	}

	@Secured
	@POST
	@Path("/image")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadImage(MultipartFormDataInput input) throws IOException {
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");

		for (InputPart inputPart : inputParts) {
			String[] contentDisposition = inputPart.getHeaders().getFirst("Content-Disposition").split(";");

			String fileName = FileUtils.getFileName(contentDisposition);
			InputStream inputStream = inputPart.getBody(InputStream.class, null);
			byte[] bytes = IOUtils.toByteArray(inputStream);
			try {
				personService.saveImage(userService.getCurrentUser().getId(), new PersonImage(fileName, bytes));
			} catch (RplException e) {
				return Response.serverError().entity(MessagePOJO.of(e.getCode(), e.getMessage())).build();
			}
		}
		return Response.status(200).build();
	}

	@Secured
	@PUT
	@Path("/information")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePersonInfoById(PersonInfoPOJO pojo) {
		try {
			personService.updatePersonInfo(userService.getCurrentUser().getId(), pojo.getName(), pojo.getMail(), pojo.getStudentId());
		} catch (RplException e) {
			return Response.serverError().entity(MessagePOJO.of(e.getCode(), e.getMessage())).build();
		}
		return Response.ok().build();
	}

}
