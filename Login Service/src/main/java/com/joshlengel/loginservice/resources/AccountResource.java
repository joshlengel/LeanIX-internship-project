package com.joshlengel.loginservice.resources;

import com.joshlengel.loginservice.LoginCredentials;
import com.joshlengel.loginservice.auth.ApiToken;
import com.joshlengel.loginservice.auth.HttpAuthService;
import com.joshlengel.loginservice.auth.NewUserRoles;
import com.joshlengel.loginservice.db.UserAccountDAO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/api/account/{username}")
public class AccountResource {

    @Inject
    UserAccountDAO userAccountDAO;

    @Inject
    HttpAuthService authService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/data")
    public String getData(@Context HttpHeaders headers,
                          @NotNull @NotEmpty @PathParam("username") String username) {
        // Validate header
        authService.authenticate(headers, ApiToken.GET_HIDDEN_DATA, username);

        return "s3cret data for user: " + username;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public void remove(@Context HttpHeaders headers,
                       @NotNull @NotEmpty @PathParam("username") String username,
                       @NotNull @Valid LoginCredentials credentials) {
        authService.authenticate(headers, ApiToken.REMOVE_USER, username);

        userAccountDAO.remove(credentials);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/permissions")
    public void promoteUser(@Context HttpHeaders headers,
                            @NotNull @NotEmpty @PathParam("username") String username,
                            @NotNull @Valid NewUserRoles newRoles) {
        authService.authenticate(headers, ApiToken.PROMOTE_USER, username);

        userAccountDAO.updateRoles(newRoles.username, newRoles.newRoles);
    }
}
