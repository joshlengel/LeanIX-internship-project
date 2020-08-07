package com.joshlengel.loginservice.resources;

import com.joshlengel.loginservice.auth.ApiToken;
import com.joshlengel.loginservice.auth.HttpAuthService;
import com.joshlengel.loginservice.db.UserAccount;
import com.joshlengel.loginservice.db.UserAccountDAO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/api/signup")
public class SignupResource {

    @Inject
    UserAccountDAO userAccountDAO;

    @Inject
    HttpAuthService authService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(@Context HttpHeaders headers,
                       @NotNull @Valid UserAccount account) {
        authService.authenticate(headers, ApiToken.ADD_USER);

        userAccountDAO.create(account);
    }
}
