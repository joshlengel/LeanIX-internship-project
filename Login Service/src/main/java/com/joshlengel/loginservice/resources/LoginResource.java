package com.joshlengel.loginservice.resources;

import com.joshlengel.encryptionservice.resources.EncryptionResource;
import com.joshlengel.loginservice.user.UserAccount;
import com.joshlengel.loginservice.user.UserDatabase;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("login")
public class LoginResource {

    @Inject
    UserDatabase database;

    EncryptionResource encryptionResource = new EncryptionResource();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("new")
    public UserAccount create(@FormParam("username") String username, @FormParam("password") String password) {
        String encryptedPassword = encryptionResource.encrypt(password);

        return database
                .add(username, encryptedPassword)
                .orElseThrow(() -> new WebApplicationException("User already exists", 400));
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserAccount login(@FormParam("username") String username, @FormParam("password") String password) {
        String encryptedPassword = encryptionResource.encrypt(password);

        return database
                .get(username, encryptedPassword)
                .orElseThrow(() -> new WebApplicationException("No such user exists", 400));
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/remove")
    public void delete(@FormParam("username") String username, @FormParam("password") String password) {
        String encryptedPassword = encryptionResource.encrypt(password);

        database.remove(username, encryptedPassword);
    }
}
