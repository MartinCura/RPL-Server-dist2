package com.rpl.model.reports;

public class Ranking {
    private Long personId;
    private String name;
    private String username;
    private String rangeName;
    private int points;
    
    public Ranking(Long personId, String name, String username, String rangeName, int points) {
        super();
        this.personId = personId;
        this.name = name;
        this.username = username;
        this.rangeName = rangeName;
        this.points = points;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getRangeName() {
        return rangeName;
    }

    public int getPoints() {
        return points;
    }
    
    
}
