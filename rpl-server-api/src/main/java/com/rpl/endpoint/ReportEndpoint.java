package com.rpl.endpoint;

import com.rpl.POJO.ActivitySubmissionSolutionPOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.ReportCourseActivitiesPOJO;
import com.rpl.POJO.ReportCourseTopicsPOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplRoleException;
import com.rpl.model.*;
import com.rpl.model.reports.Report1;
import com.rpl.model.reports.Report2;
import com.rpl.model.reports.Report5;
import com.rpl.model.reports.Report6;
import com.rpl.security.SecurityHelper;
import com.rpl.service.*;
import com.rpl.service.util.Utils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Secured
@Path("/reports")
public class ReportEndpoint {
	@Inject
	private CourseService courseService;
	@Inject
	private TopicService topicService;
	@Inject
	private ReportService reportService;
	@Inject
	private ActivitySubmissionService activitySubmissionService;
	@Inject
	private UserService userService;
	@Inject
	private PersonService personService;

	@GET
	@Path("/course/{id}/activities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsActivities(@PathParam("id") Long courseId) {
		Course course = courseService.getCourseById(courseId);
		CoursePerson person = personService.getCoursePersonByIdAndCourse(userService.getCurrentUser().getId(), courseId);

		List<CoursePerson> persons;
		Map<Activity, List<ActivitySubmission>> submissionsByActivity;
		if (person.getRole().equals(RoleCourse.ASSISTANT_PROFESSOR)) {
			persons = courseService.getStudentsByAssistant(courseId, person.getPerson().getId());
			submissionsByActivity = reportService.getActivityReportByCourse(courseId, person.getPerson().getId());
		} else {
			persons = courseService.getStudents(courseId);
			submissionsByActivity = reportService.getActivityReportByCourse(courseId, null);
		}
		return Response.status(200).entity(new ReportCourseActivitiesPOJO(course, persons, submissionsByActivity)).build();
	}

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
	@Path("/course/{id}/topics")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentsTopics(@PathParam("id") Long courseId) {
		Course course = courseService.getCourseById(courseId);
		CoursePerson person = personService.getCoursePersonByIdAndCourse(userService.getCurrentUser().getId(), courseId);

		List<CoursePerson> students;
		Map<Topic, List<ActivitySubmission>> submissionsByTopic;
		if (person.getRole().equals(RoleCourse.ASSISTANT_PROFESSOR)) {
			students = courseService.getStudentsByAssistant(courseId, person.getPerson().getId());
			submissionsByTopic = reportService.getTopicReportByCourse(courseId, person.getPerson().getId());
		} else {
			students = courseService.getStudents(courseId);
			submissionsByTopic = reportService.getTopicReportByCourse(courseId, null);
		}

		List<Topic> topics = topicService.getTopicsByCourse(courseId);

		return Response.status(200).entity(new ReportCourseTopicsPOJO(course, students, topics, submissionsByTopic)).build();
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
	@Path("/5/{topicId}")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Listado de alumnos con porcentaje mínimo definible de completado en cierta categoría. Muestra alumno y porcentaje.
	 */
	public Response getReport5(@PathParam("topicId") Long topicId) {
		List<Report5> report = reportService.getReport5(topicId);

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

}
