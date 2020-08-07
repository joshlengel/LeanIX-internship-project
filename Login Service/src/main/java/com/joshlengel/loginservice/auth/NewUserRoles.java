package com.joshlengel.loginservice.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class NewUserRoles {

    @JsonProperty
    @NotNull
    @NotEmpty
    public String username;

    @JsonProperty
    @NotNull
    @NotEmpty
    public String newRoles;
}
