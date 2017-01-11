package com.rpl.serviceImpl;

import javax.ejb.Stateful;

import com.rpl.model.Person;
import com.rpl.service.UserService;

@Stateful
public class UserServiceImpl implements UserService {

	private Person currentUser;

	public Person getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Person p) {
		this.currentUser = p;
	}

}
