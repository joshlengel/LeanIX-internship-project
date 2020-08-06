package com.joshlengel.loginservice.resources;

import com.joshlengel.loginservice.LoginCredentials;
import com.joshlengel.loginservice.auth.AuthService;
import com.joshlengel.loginservice.db.UserAccount;
import com.joshlengel.loginservice.db.UserAccountDAO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/login")
public class LoginResource {

    @Inject
    AuthService authService;

    @Inject
    UserAccountDAO userAccountDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(@NotNull @Valid LoginCredentials credentials) {
        UserAccount account = userAccountDAO.find(credentials).orElseThrow(() ->
                new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED));

        return authService.getToken(account);
    }
}
