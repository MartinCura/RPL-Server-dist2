package com.rpl.serviceImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import com.rpl.exception.RplException;
import com.rpl.model.CoursePerson;
import com.rpl.model.DatabaseState;
import com.rpl.model.MessageCodes;
import com.rpl.model.Person;
import com.rpl.model.PersonImage;
import com.rpl.persistence.CoursePersonDAO;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.PersonService;
import com.rpl.service.UserService;
import com.rpl.service.util.FileUtils;

@Stateless
public class PersonServiceImpl implements PersonService {

	@Inject
	private PersonDAO personDAO;
	@Inject
	private CoursePersonDAO coursePersonDAO;

	@Inject
	private UserService userService;

	public List<Person> getPersons() {
		return personDAO.findAll();
	}

	public Person getPersonById(Long id) {
		return personDAO.find(id);
	}

	public Person getPersonByUsername(String username) {
		try {
			return personDAO.findByUsername(username);
		} catch (NoResultException e) {
			return null;
		}
	}

	public void addCoursePerson(CoursePerson coursePerson) throws RplException {
		coursePerson.setState(DatabaseState.ENABLED);
		try {
			coursePersonDAO.save(coursePerson);
		} catch (NoResultException e) {
			throw RplException.of(MessageCodes.ERROR_INEXISTENT_USER, "");
		} catch (PersistenceException e) {
			throw RplException.of(MessageCodes.ERROR_USER_ALREADY_ASSIGNED, "");
		}

	}

	public void updatePersonInfo(Long id, String name, String mail, Long studentId) throws RplException {
		try {
			personDAO.updatePersonInfo(id, name, mail, studentId);

		} catch (PersistenceException e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				ConstraintViolationException castedE = (ConstraintViolationException) e.getCause();
				if (castedE.getConstraintName().contains("mail"))
					throw RplException.of(MessageCodes.ERROR_MAIL_ALREADY_EXISTS, "Mail debe ser unico");
				else
					throw RplException.of(MessageCodes.ERROR_USERNAME_ALREADY_EXISTS, "Username debe ser unico");
			}
			throw RplException.of(MessageCodes.SERVER_ERROR, "");
		}
	}

	public void updatePersonInfo(Long id, String name, String mail, Long studentId, String role) throws RplException {
		try {
			personDAO.updatePersonInfo(id, name, mail, studentId, role);

		} catch (PersistenceException e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				ConstraintViolationException castedE = (ConstraintViolationException) e.getCause();
				if (castedE.getConstraintName().contains("mail"))
					throw RplException.of(MessageCodes.ERROR_MAIL_ALREADY_EXISTS, "Mail debe ser unico");
				else
					throw RplException.of(MessageCodes.ERROR_USERNAME_ALREADY_EXISTS, "Username debe ser unico");
			}
			throw RplException.of(MessageCodes.SERVER_ERROR, "");
		}
	}

	public CoursePerson getCoursePersonByIdAndCourse(Long personId, Long courseId) {
		return coursePersonDAO.findByCourseAndPerson(courseId, personId);
	}

	public void saveImage(Long id, PersonImage personImage) throws RplException {
		FileUtils.validateFile(personImage.getContent());
		Person currentUser = userService.getCurrentUser();
		PersonImage recoveredFile = personDAO.findFileByPerson(id);
		if (recoveredFile != null) {
			recoveredFile.setFileName(personImage.getFileName());
			recoveredFile.setContent(personImage.getContent());
			personDAO.save(recoveredFile);
			return;
		}
		personImage.setPerson(currentUser);
		personDAO.save(personImage);
	}

	public void deletePersonById(Long id) {
		personDAO.delete(id);
	}

}