package com.rpl.POJO;

import com.rpl.model.Person;

public class PersonPOJO {

    private String name;
    private String mail;

    public PersonPOJO(Person person) {
        this.name = person.getName();
        this.mail = person.getMail();
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

}
