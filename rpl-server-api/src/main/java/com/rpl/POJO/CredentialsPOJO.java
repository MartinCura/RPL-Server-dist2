package com.rpl.POJO;

public class CredentialsPOJO {

    private String username;
    private String password;

    public CredentialsPOJO() {
    }

    public CredentialsPOJO(String user, String passwd) {
        this.username = user;
        this.password = passwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
