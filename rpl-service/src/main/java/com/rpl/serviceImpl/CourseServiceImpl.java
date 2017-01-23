package com.rpl.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.CoursePerson;
import com.rpl.model.Person;
import com.rpl.model.RoleCourse;
import com.rpl.persistence.CourseDAO;
import com.rpl.persistence.CoursePersonDAO;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.CourseService;
import com.rpl.service.UserService;

@Stateless
public class CourseServiceImpl implements CourseService{
    @Inject
    private UserService userService;
    @Inject
    private CourseDAO courseDAO;
    @Inject
    private CoursePersonDAO coursePersonDAO;
    @Inject
    private PersonDAO personDAO;

    public List<Course> getCourses() {
        return courseDAO.findAll();
    }

    public List<Course> getCoursesByRole(String role) {
        Person person = userService.getCurrentUser();
        return courseDAO.findByPersonRole(person.getId(), RoleCourse.valueOf(role));
    }

    public List<Course> getUnregisteredCourses() {
        Person person = userService.getCurrentUser();
        return courseDAO.findUnregisteredByPerson(person.getId());
    }

    public Course getCourseById(Long id) {
        return courseDAO.find(id);
    }

    public Course submit(Course course) {
        return courseDAO.save(course);
    }
    
    public void deleteCourseById(Long id){
    	courseDAO.delete(id);
    }

    public void join(Long courseId) {
        Person person = userService.getCurrentUser();
        Course course = courseDAO.find(courseId);
        CoursePerson coursePerson = new CoursePerson();
        coursePerson.setPerson(person);
        coursePerson.setCourse(course);
        coursePerson.setRole(RoleCourse.STUDENT);
        coursePersonDAO.save(coursePerson);

    }

    public Map<Person, Set<ActivitySubmission>> getSubmissionsByStudent(Long id) {
        Map<Person, Set<ActivitySubmission>> submissionsByStudent = new HashMap<Person, Set<ActivitySubmission>>();
        List<Person> students = personDAO.findByCourse(id);
        //FIXME devolver solo las submissions de este curso y que fueron SUCCESS
        for (Person person : students) {
            submissionsByStudent.put(person, person.getSubmissions());
        }
        return submissionsByStudent;

    }
    
	public void updateCustomization(Long id, String customization){
		courseDAO.updateCustomization(id, customization);
	}

    public void accept(Long courseId, Long personId) {
        coursePersonDAO.acceptStudent(courseId, personId);
    }

    public List<CoursePerson> getStudents(Long id) {
        return coursePersonDAO.findByCourseIdAndRole(id, RoleCourse.STUDENT);
    }

    public List<CoursePerson> getAssistants(Long id) {
        return coursePersonDAO.findByCourseIdAndRole(id, RoleCourse.ASSISTANT_PROFESSOR);
    }

    public void assignAssistant(Long courseId, Long student, Long assistant) {
        coursePersonDAO.updateAssistant(courseId, student, assistant);
    }
}
