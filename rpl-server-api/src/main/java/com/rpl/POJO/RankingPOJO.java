package com.rpl.POJO;

import com.rpl.model.Person;

public class RankingPOJO {

    private int pos;
    private String name;
    private int points;

    public RankingPOJO(Person person, int points) {
        this.pos = pos;
        this.name = person.getName();
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

    public void setPos(int pos) {
        this.pos = pos;
    }
}
