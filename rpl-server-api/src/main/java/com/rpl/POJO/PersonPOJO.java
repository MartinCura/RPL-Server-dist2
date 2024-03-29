package com.rpl.POJO;

import com.rpl.model.Person;

public class PersonPOJO {

    private Long id;
    private String username;
    private String name;
    private String mail;

    public PersonPOJO(Person person) {
        this.id = person.getId();
        this.username = person.getCredentials().getUsername();
        this.name = person.getName();
        this.mail = person.getMail();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

}
