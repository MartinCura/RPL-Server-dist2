package com.rpl.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rpl.POJO.AssistantPOJO;
import com.rpl.POJO.CoursePOJO;
import com.rpl.POJO.MessagePOJO;
import com.rpl.POJO.ProfessorPOJO;
import com.rpl.POJO.input.CourseInputPOJO;
import com.rpl.POJO.input.CoursePersonInputPOJO;
import com.rpl.annotation.Secured;
import com.rpl.exception.RplException;
import com.rpl.model.Course;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;
import com.rpl.model.Role;
import com.rpl.model.RoleCourse;
import com.rpl.service.CourseService;
import com.rpl.service.PersonService;

@Secured(Role.ADMIN)
@Path("/admin")
public class AdminEndpoint {

    @Inject
    private CourseService courseService;
    @Inject
    private PersonService personService;

    @GET
    @Path("/courses")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCourses() {
    	List<Course> result = courseService.getCourses();
    	List<CoursePOJO> resultPOJO = result.stream().map(c -> CoursePOJO.mapWithoutTopics(c)).collect(Collectors.toList());
        return Response.ok(resultPOJO).build();
    }
    
    @POST
    @Path("/courses")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCourse(CourseInputPOJO courseInputPOJO) {
        Course course = new Course();
        course.setName(courseInputPOJO.getName());
        courseService.submit(course);
        return Response.status(200).build();
    }

    @POST
    @Path("/courses/{courseId}/person")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPersonToCourse(@PathParam("courseId") Long id, CoursePersonInputPOJO coursePersonInputPOJO) {
        Course course = courseService.getCourseById(id);
        Person person = personService.getPersonByUsername(coursePersonInputPOJO.getUsername());
        if (person == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(MessagePOJO.of("User not found")).build();
        }

        CoursePerson coursePerson = new CoursePerson();
        coursePerson.setCourse(course);
        coursePerson.setPerson(person);
        coursePerson.setRole(RoleCourse.valueOf(coursePersonInputPOJO.getRole()));
        coursePerson.setAccepted(true);
        try {
			personService.addCoursePerson(coursePerson);
		} catch (RplException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(MessagePOJO.of(e.getCode(), e.getMsg())).build();
		}
        return Response.status(200).build();
    }
    
    
	@GET
	@Path("/{id}/assistants")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAssistantsAdmin(@PathParam("id") Long id) {
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
		List<CoursePerson> professors = courseService.getProfessors(id);

		List<ProfessorPOJO> professorPOJOS = professors.stream().map(coursePerson -> new ProfessorPOJO(coursePerson))
				.collect(Collectors.toList());

		return Response.status(200).entity(professorPOJOS).build();
	}

}
