package com.joshlengel.loginservice.resources;

import com.joshlengel.loginservice.LoginCredentials;
import com.joshlengel.loginservice.auth.AuthService;
import com.joshlengel.loginservice.db.UserAccountDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/account/{username}")
public class AccountResource {

    @Inject
    UserAccountDAO userAccountDAO;

    @Inject
    AuthService authService;

    @POST
    @Path("authenticate")
    public void checkSession(@Context HttpHeaders headers,
                             @NotNull @NotEmpty @PathParam("username") String username) {
        Jws<Claims> claims = authService.validateToken(headers.getHeaderString("Authorization"));

        if (!claims.getBody().getSubject().equals(username))
            throw new WebApplicationException("Invalid token", Response.Status.UNAUTHORIZED);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/data")
    public String getData(@Context HttpHeaders headers,
                          @NotNull @NotEmpty @PathParam("username") String username) {
        Jws<Claims> claims = authService.validateToken(headers.getHeaderString("Authorization"));

        if (!claims.getBody().getSubject().equals(username))
            throw new WebApplicationException("Invalid token", Response.Status.UNAUTHORIZED);

        return "s3cret data for user: " + username;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public void remove(@Context HttpHeaders headers,
                       @NotNull @Valid LoginCredentials credentials) {
        authService.validateToken(headers.getHeaderString("Authorization"));

        userAccountDAO.remove(credentials);
    }
}
