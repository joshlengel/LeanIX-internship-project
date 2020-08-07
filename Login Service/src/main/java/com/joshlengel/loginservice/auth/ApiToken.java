package com.joshlengel.loginservice.auth;

import java.util.Arrays;
import java.util.List;

public enum ApiToken {

    GET_HIDDEN_DATA("sysadmin", "admin", "user"),
    ADD_USER("sysadmin", "admin"),
    REMOVE_USER("sysadmin", "admin"),
    PROMOTE_USER("sysadmin", "admin"),
    DROP_USER_TABLE("sysadmin");

    private final List<String> roles;

    ApiToken(String ...roles) { this.roles = Arrays.asList(roles); }

    public List<String> getRoles() { return roles; }
}
