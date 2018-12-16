package com.rpl.POJO;

import com.rpl.model.Role;

public class TokenPOJO {

    private final String token;
    private final Role role;
    private final String username;

    private TokenPOJO(String token, Role rol, String username){
        this.token = token;
        this.role = rol;
        this.username = username;
    }

    public static TokenPOJO of (String token, Role rol, String username){
        return new TokenPOJO(token, rol, username);
    }

    public String getToken() {
        return token;
    }

    public Role getRol() {
        return role;
    }

    public String getUsername(){
        return username;
    }
}
