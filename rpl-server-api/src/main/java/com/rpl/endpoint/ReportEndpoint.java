package com.rpl.endpoint;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.ActivitySubmissionSolutionPOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.ReportCoursePOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplRoleException;
import com.rpl.model.*;
import com.rpl.model.reports.*;
import com.rpl.security.SecurityHelper;
import com.rpl.service.*;
import com.rpl.service.util.Utils;

@Secured
@Path("/reports")
public class ReportEndpoint {
	@Inject
	private ReportService reportService;
	@Inject
	private ActivityService activityService;
	@Inject
	private ActivitySubmissionService activitySubmissionService;
	@Inject
	private UserService userService;
	@Inject
	private PersonService personService;
	@Inject
	private TopicService topicService;

	@GET
	@Path("/submission/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSubmissionCode(@PathParam("id") Long submissionId) {
		ActivitySubmission submission = activitySubmissionService.getSubmissionById(submissionId);
		try {
			SecurityHelper.checkPermissions(submission.getActivity().getTopic().getCourse().getId(),
					Utils.listOf(RoleCourse.PROFESSOR, RoleCourse.ASSISTANT_PROFESSOR), userService.getCurrentUser());
		} catch (RplRoleException e) {
			return Response.ok(MessagePOJO.of(MessageCodes.ERROR_ROLE_NOT_ALLOWED, "")).build();
		}
		return Response.status(200).entity(new ActivitySubmissionSolutionPOJO(submission)).build();
	}

	@GET
	@Path("/1/{courseId}/{personId}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Por alumno, por actividad cantidad de submissions hasta que se marcó como definitiva y si fue resuelta.
	 * Se debe seleccionar un alumno, y opcionalmente, la categoría.
	 */
	public Response getReport1(@PathParam("courseId") Long courseId, @PathParam("personId") Long personId, @QueryParam("topicId") Long topicId) {
		List<Report1> report = reportService.getReport1(courseId, personId, topicId);

		return Response.status(200).entity(report).build();
	}

	@GET
	@Path("/2/{topicId}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Promedio por actividad, es decir, cuántas submissions realizó cada alumno hasta llegar a la solución definitiva promediadas.
	 * Si no hubo solución definitiva, no se tomar en cuenta. Se debe elegir la categoría de las actividades a mostrar
	 */
	public Response getReport2(@PathParam("topicId") Long topicId) {
		List<Report2> report = reportService.getReport2(topicId);

		return Response.status(200).entity(report).build();
	}

	@GET
	@Path("/3/{topicId}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Muestra las actividades de la categoría seleccionada y si fue o no resuelta por cada alumno (cada fila es un alumno)
	 */
	public Response getReport3(@PathParam("topicId") Long topicId) {
		Course course = topicService.getTopicById(topicId).getCourse();
		CoursePerson person = personService.getCoursePersonByIdAndCourse(userService.getCurrentUser().getId(), course.getId());

		List<Activity> activities = activityService.getActivitiesByTopic(topicId);
		List<Report3> report;
		if (person.getRole().equals(RoleCourse.ASSISTANT_PROFESSOR)) {
			report = reportService.getReport3ByAssistant(topicId, person.getPerson().getId());
		} else {
			report = reportService.getReport3(topicId);
		}

		return Response.status(200).entity(new ReportCoursePOJO(activities, report)).build();
	}

	@GET
	@Path("/5/{topicId}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Listado de alumnos con porcentaje mínimo definible de completado en cierta categoría. Muestra alumno y porcentaje.
	 */
	public Response getReport5(@PathParam("topicId") Long topicId) {
		Course course = topicService.getTopicById(topicId).getCourse();
		CoursePerson person = personService.getCoursePersonByIdAndCourse(userService.getCurrentUser().getId(), course.getId());

		List<Report5> report;
		if (person.getRole().equals(RoleCourse.ASSISTANT_PROFESSOR)) {
			report = reportService.getReport5ByAssistant(topicId, person.getPerson().getId());
		} else {
			report = reportService.getReport5(topicId);
		}

		return Response.status(200).entity(report).build();
	}

	@GET
	@Path("/6/{topicId}/{personId}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Por categoría y alumno, eje X: Actividades ordenadas por la dificultad (puntaje definido); eje Y: Cantidad de intentos
	 */
	public Response getReport6(@PathParam("topicId") Long topicId, @PathParam("personId") Long personId) {
		List<Report6> report = reportService.getReport6(topicId, personId);

		return Response.status(200).entity(report).build();
	}

	@GET
	@Path("/ranking/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReport7(@PathParam("courseId") Long courseId, @QueryParam("date") String date) {
		CoursePerson person = personService.getCoursePersonByIdAndCourse(userService.getCurrentUser().getId(), courseId);

		List<Report7> report;
		if (person.getRole().equals(RoleCourse.ASSISTANT_PROFESSOR)) {
			report = reportService.getReport7ByAssistant(courseId, date, person.getPerson().getId());
		} else {
			report = reportService.getReport7(courseId, date);
		}
		return Response.status(200).entity(report).build();
	}
}
