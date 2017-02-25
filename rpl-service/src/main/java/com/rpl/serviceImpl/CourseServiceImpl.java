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
import com.rpl.model.DatabaseState;
import com.rpl.model.Person;
import com.rpl.model.RoleCourse;
import com.rpl.persistence.CourseDAO;
import com.rpl.persistence.CoursePersonDAO;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.ActionLogService;
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
    @Inject
    private ActionLogService actionLogService;

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

    public Map<Long, Boolean> getCoursesInscripted() {
        Map<Long, Boolean> coursesInscripted = new HashMap<Long, Boolean>();
        List<CoursePerson> courses = coursePersonDAO.findByPerson(userService.getCurrentUser().getId());
        for (CoursePerson cp : courses) {
            coursesInscripted.put(cp.getCourse().getId(), cp.isAccepted());
        }
        return coursesInscripted;
    }

    public Course getCourseById(Long id) {
        return courseDAO.find(id);
    }

    public Course submit(Course course) {
    	Course newCourse = courseDAO.save(course);
    	actionLogService.logNewCourse(course.getId());
        return newCourse;
    }
    
    public void deleteCourseById(Long id){
    	courseDAO.delete(id);
    	actionLogService.logDeletedCourse(id);
    }

    public void join(Long courseId) {
        Person person = userService.getCurrentUser();
        Course course = courseDAO.find(courseId);
        CoursePerson coursePerson = new CoursePerson();
        coursePerson.setPerson(person);
        coursePerson.setCourse(course);
        coursePerson.setRole(RoleCourse.STUDENT);
        coursePerson.setState(DatabaseState.ENABLED);
        coursePersonDAO.save(coursePerson);
        actionLogService.logJoinedCourse(courseId, coursePerson.getPerson().getId());
    }
    
    public void leaveCourse(Long courseId, Long personId) {
        coursePersonDAO.deleteByPersonId(courseId, personId);
        actionLogService.logLeftCourse(courseId, personId);
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
    
    public void pending(Long courseId, Long personId) {
        coursePersonDAO.pendingStudent(courseId, personId);
    }
    
    
    public List<CoursePerson> getStudents(Long id) {
        return coursePersonDAO.findByCourseIdAndRole(id, RoleCourse.STUDENT);
    }

    public List<CoursePerson> getAssistants(Long id) {
        return coursePersonDAO.findByCourseIdAndRole(id, RoleCourse.ASSISTANT_PROFESSOR);
    }
    
	public List<CoursePerson> getProfessors(Long id) {
		return coursePersonDAO.findByCourseIdAndRole(id, RoleCourse.PROFESSOR);
	}

    public void assignAssistant(Long courseId, Long student, Long assistant) {
        coursePersonDAO.updateAssistant(courseId, student, assistant);
    }
    
    public void updateCourseName(Long id, String name){
    	courseDAO.updateCourseName(id, name);
    }
    
    public void updateDescRulesAndCustomization(Long id, String customization, String description, String rules){
    	courseDAO.updateDescRulesAndCustomization(id, customization, description, rules);
    }
}
