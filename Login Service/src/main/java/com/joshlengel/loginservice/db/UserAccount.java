package com.joshlengel.loginservice.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

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

    @JsonIgnore
    @Transient
    private List<String> cacheRoles;

    @JsonIgnore
    public List<String> getRoles() {
        if (cacheRoles == null) {
            cacheRoles = Arrays.asList(roles.trim().split("\\s*,\\s*"));
        }

        return cacheRoles;
    }
}
