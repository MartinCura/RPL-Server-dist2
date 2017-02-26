package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.ActivityPOJO;
import com.rpl.POJO.AssistantPOJO;
import com.rpl.POJO.ProfessorPOJO;
import com.rpl.POJO.StudentPOJO;
import com.rpl.POJO.TopicPOJO;
import com.rpl.annotation.Secured;
import com.rpl.model.Activity;
import com.rpl.model.CoursePerson;
import com.rpl.model.Topic;
import com.rpl.service.ActivityService;
import com.rpl.service.CourseService;
import com.rpl.service.TopicService;

@Secured
@Path("/manage/courses")
public class ManageEndpoint {

	@Inject
	private CourseService courseService;
	@Inject
	private ActivityService activityService;
	@Inject
	private TopicService topicService;

	@GET
	@Path("/{id}/activities")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActivities(@PathParam("id") Long courseId) {
		//TODO professor
		List<Activity> activities = activityService.getActivitiesByCourse(courseId);
		List<ActivityPOJO> activityPOJOS = new ArrayList<ActivityPOJO>();
		for (Activity activity : activities) {
			activityPOJOS.add(new ActivityPOJO(activity));
		}
		return Response.status(200).entity(activityPOJOS).build();
	}

	@GET
	@Path("/{id}/topics")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopics(@PathParam("id") Long courseId) {
		//TODO professor
		List<Topic> topics = topicService.getTopicsByCourse(courseId);
		List<TopicPOJO> topicPOJOS = new ArrayList<TopicPOJO>();
		for (Topic topic : topics) {
			topicPOJOS.add(new TopicPOJO(topic));
		}
		return Response.status(200).entity(topicPOJOS).build();
	}

	@GET
	@Path("/{id}/students")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudents(@PathParam("id") Long id) {
		//TODO professor
		List<CoursePerson> students = courseService.getStudents(id);
		List<StudentPOJO> studentPOJOS = new ArrayList<StudentPOJO>();
		for (CoursePerson student : students) {
			studentPOJOS.add(new StudentPOJO(student));
		}
		return Response.status(200).entity(studentPOJOS).build();
	}

	@GET
	@Path("/{id}/assistants")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAssistants(@PathParam("id") Long id) {
		//TODO professor
		List<CoursePerson> assistants = courseService.getAssistants(id);
		List<AssistantPOJO> assistantPOJOS = new ArrayList<AssistantPOJO>();
		for (CoursePerson assistant : assistants) {
			assistantPOJOS.add(new AssistantPOJO(assistant));
		}
		return Response.status(200).entity(assistantPOJOS).build();
	}

	@GET
	@Path("/{id}/professors")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfessors(@PathParam("id") Long id) {
		//TODO professor
		List<CoursePerson> professors = courseService.getProfessors(id);

		List<ProfessorPOJO> professorPOJOS = professors.stream().map(coursePerson -> new ProfessorPOJO(coursePerson))
				.collect(Collectors.toList());

		return Response.status(200).entity(professorPOJOS).build();
	}
}
