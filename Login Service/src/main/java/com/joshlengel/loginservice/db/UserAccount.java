package com.joshlengel.loginservice.db;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class UserAccount {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    public Long id;

    @JsonProperty
    @NotNull
    @NotEmpty
    @Column(name = "username", unique = true, nullable = false)
    public String username;

    @JsonProperty
    @NotNull
    @NotEmpty
    @Column(name = "password", nullable = false)
    public String password;

    @JsonProperty
    @NotNull
    @NotEmpty
    @Column(name = "roles", nullable = false)
    public String roles;
}
