package com.rpl.endpoint;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.exception.RplQueueException;
import com.rpl.model.Language;
import com.rpl.model.SolutionSubmission;
import com.rpl.service.SolutionService;

@Path("/solution")
public class SolutionEndpoint {

	@Inject
	private SolutionService solutionService;

	@POST
	@Path("/submit/{id}/{lang}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response submit(@PathParam("id") Long id, @PathParam("lang") String sLang, String solutionContent) {
		//TODO El id no puede enviarse asi por un tema de seguridad, hay que sacarlo de algun lado a definir, hay que verlo.
		
		SolutionSubmission sub = new SolutionSubmission();
		sub.setContent(solutionContent);
		sub.setLanguage(Language.valueOf(sLang.toUpperCase()));
		try {
			solutionService.submit(id, sub);
		} catch (RplQueueException e) {
			return Response.status(505).entity(e).build();
		}

		return Response.status(200).build();

	}
}