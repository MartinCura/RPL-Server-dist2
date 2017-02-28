package com.rpl.serviceImpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import com.rpl.exception.RplException;
import com.rpl.model.CoursePerson;
import com.rpl.model.DatabaseState;
import com.rpl.model.MessageCodes;
import com.rpl.model.Person;
import com.rpl.persistence.CoursePersonDAO;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.PersonService;

@Stateless
public class PersonServiceImpl implements PersonService {

	@Inject
	private PersonDAO personDAO;
	@Inject
	private CoursePersonDAO coursePersonDAO;

	public List<Person> getPersons() {
		return personDAO.findAll();
	}

	public Person getPersonById(Long id) {
		return personDAO.find(id);
	}

	public Person getPersonByUsername(String username) {
		try {
			return personDAO.findByUsername(username);
		} catch (NoResultException e){
			return null;
		}
	}

	public void addCoursePerson(CoursePerson coursePerson) throws RplException {
		coursePerson.setState(DatabaseState.ENABLED);
		try {
			coursePersonDAO.save(coursePerson);
		} catch (NoResultException e){
			throw RplException.of(MessageCodes.ERROR_INEXISTENT_USER, "");
		} catch (PersistenceException e){
			throw RplException.of(MessageCodes.ERROR_USER_ALREADY_ASSIGNED, "");
		}
		
	}

	public void updatePersonInfo(Long id, String name, String mail) {
		personDAO.updatePersonInfo(id, name, mail);
	}
}