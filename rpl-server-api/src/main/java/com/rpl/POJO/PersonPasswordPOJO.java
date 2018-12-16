package com.rpl.POJO;

import com.rpl.model.Person;

public class PersonPasswordPOJO {

    private String password;

    public PersonPasswordPOJO() {
    }

    public PersonPasswordPOJO(Person p) {
        this.password = p.getCredentials().getPassword();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
