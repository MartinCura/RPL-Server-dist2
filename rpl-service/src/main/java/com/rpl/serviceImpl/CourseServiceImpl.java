package com.rpl.serviceImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.rpl.exception.RplException;
import com.rpl.model.ActivitySubmission;
import com.rpl.model.Course;
import com.rpl.model.CourseImage;
import com.rpl.model.CoursePerson;
import com.rpl.model.DatabaseState;
import com.rpl.model.MessageCodes;
import com.rpl.model.Person;
import com.rpl.model.Range;
import com.rpl.model.RoleCourse;
import com.rpl.persistence.ActivitySubmissionDAO;
import com.rpl.persistence.CourseDAO;
import com.rpl.persistence.CoursePersonDAO;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.ActionLogService;
import com.rpl.service.CourseService;
import com.rpl.service.UserService;
import com.rpl.service.util.FileUtils;

@Stateless
public class CourseServiceImpl implements CourseService {
	@Inject
	private UserService userService;
	@Inject
	private CourseDAO courseDAO;
	@Inject
	private CoursePersonDAO coursePersonDAO;
	@Inject
	private PersonDAO personDAO;
	@Inject
	private ActivitySubmissionDAO activitySubmissionDAO;
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

	public Map<Long, CoursePerson> getCoursesInscripted() {
		List<CoursePerson> coursesPerson = coursePersonDAO.findByPerson(userService.getCurrentUser().getId());

		Map<Long, CoursePerson> coursesInscripted = new HashMap<Long, CoursePerson>();
		for (CoursePerson cp : coursesPerson) {
			coursesInscripted.put(cp.getCourse().getId(), cp);
		}
		return coursesInscripted;
	}

	public Course getCourseById(Long id) {
		return courseDAO.find(id);
	}

	public Course submit(Course course) {
		Course newCourse = courseDAO.save(course);
		actionLogService.logNewCourse(newCourse.getId());
		return newCourse;
	}

	public void deleteCourseById(Long id) {
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

	public void updateCustomization(Long id, String customization) {
		courseDAO.updateCustomization(id, customization);
	}

	public void accept(Long courseId, Long personId) {
		coursePersonDAO.acceptStudent(courseId, personId);
	}

	public void pending(Long courseId, Long personId) {
		coursePersonDAO.pendingStudent(courseId, personId);
	}

	public List<CoursePerson> getStudents(Long courseId) {
		return coursePersonDAO.findByCourseIdAndRole(courseId, RoleCourse.STUDENT);
	}

	public List<CoursePerson> getStudentsByAssistant(Long courseId, Long assistantId) {
		return coursePersonDAO.findStudentsByAssistant(courseId, assistantId);
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

	public void updateCourseName(Long id, String name) {
		courseDAO.updateCourseName(id, name);
	}

	public void updateDescRulesAndCustomization(Long id, String customization, String description, String rules) {
		courseDAO.updateDescRulesAndCustomization(id, customization, description, rules);
	}

	public Map<Person, Integer> getPointsByPerson(Long courseId) {
		Map<Person, Integer> pointsByPerson = new HashMap<Person, Integer>();
		List<ActivitySubmission> submissions = activitySubmissionDAO.findSelectedByCourse(courseId);
		for (ActivitySubmission submission : submissions) {
			if (!pointsByPerson.containsKey(submission.getPerson())) {
				pointsByPerson.put(submission.getPerson(), 0);
			}
			pointsByPerson.put(submission.getPerson(),
					pointsByPerson.get(submission.getPerson()) + submission.getActivity().getPoints());
		}
		return pointsByPerson;
	}

	@Override
	public void saveImage(Long courseId, CourseImage courseImage) throws RplException {
		FileUtils.validateFile(courseImage.getContent());
		Course course = courseDAO.find(courseId);
		CourseImage recoveredFile = courseDAO.findFileByCourseAndName(courseId, courseImage.getFileName());
		if (recoveredFile != null) {
			recoveredFile.setContent(courseImage.getContent());
			courseDAO.save(recoveredFile);
			return;
		}
		courseImage.setCourse(course);
		courseDAO.save(courseImage);

	}

	public void updateRanges(Long courseId, List<Range> ranges) throws RplException {
		List<Integer> everyMinScoreOnRanges = ranges.stream().map(r -> r.getMinScore()).collect(Collectors.toList());
		Boolean repeatedMinScore = everyMinScoreOnRanges.stream()
				.anyMatch(min -> Collections.frequency(everyMinScoreOnRanges, min) > 1);
		if (repeatedMinScore) throw RplException.of(MessageCodes.ERROR_INVALID_RANGE_NUMBER, "");
		courseDAO.updateCourseRanges(courseId, ranges);
	}

}
