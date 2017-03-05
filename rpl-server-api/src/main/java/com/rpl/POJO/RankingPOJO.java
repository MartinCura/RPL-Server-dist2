package com.rpl.POJO;

import com.rpl.model.Person;

public class RankingPOJO {

    private int pos;
    private Long personId;
    private String name;
    private String username;
    private String rangeName;
    private int points;

    public RankingPOJO(Person person, int points, String rangeName) {
        this.pos = pos;
        this.setRangeName(rangeName);
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

	public String getRangeName() {
		return rangeName;
	}

	public void setRangeName(String rangeName) {
		this.rangeName = rangeName;
	}
}
