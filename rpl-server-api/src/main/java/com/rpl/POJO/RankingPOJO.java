package com.rpl.POJO;

import com.rpl.model.Person;

public class RankingPOJO {

    private int pos;
    private Long personId;
    private String name;
    private String username;
    private int points;

    public RankingPOJO(Person person, int points) {
        this.pos = pos;
        this.personId = person.getId();
        this.name = person.getName();
        this.username = person.getCredentials().getUsername();
        this.points = points;
    }

    public int getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getUsername() {
        return username;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
