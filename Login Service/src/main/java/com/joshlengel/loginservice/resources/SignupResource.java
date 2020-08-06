package com.joshlengel.loginservice.resources;

import com.joshlengel.loginservice.db.UserAccount;
import com.joshlengel.loginservice.db.UserAccountDAO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/signup")
public class SignupResource {

    @Inject
    UserAccountDAO userAccountDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@NotNull @Valid UserAccount account) {
        userAccountDAO.create(account);
    }
}
