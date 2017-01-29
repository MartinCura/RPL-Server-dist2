package com.rpl.serviceImpl;

import com.rpl.model.CoursePerson;
import com.rpl.model.Person;
import com.rpl.persistence.CoursePersonDAO;
import com.rpl.persistence.PersonDAO;
import com.rpl.service.PersonService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

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
		return personDAO.findByUsername(username);
	}

	public void addCoursePerson(CoursePerson coursePerson) {
		coursePersonDAO.save(coursePerson);
	}

	public void updatePersonInfo(Long id, String name, String mail) {
		personDAO.updatePersonInfo(id, name, mail);
		
	}
}