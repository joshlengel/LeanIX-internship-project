package com.joshlengel.loginservice.resources;

import com.joshlengel.encryptionservice.resources.EncryptionResource;
import com.joshlengel.loginservice.user.User;
import com.joshlengel.loginservice.user.UserDatabase;
import com.joshlengel.loginservice.user.impl.UserDatabseImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("login")
public class LoginResource {

    UserDatabase database = new UserDatabseImpl();

    EncryptionResource encryptionResource = new EncryptionResource();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("new")
    public User create(@FormParam("username") String username, @FormParam("password") String password) {
        String encryptedPassword = encryptionResource.encrypt(password);

        return database.add(username, encryptedPassword);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public User login(@FormParam("username") String username, @FormParam("password") String password) {
        String encryptedPassword = encryptionResource.encrypt(password);

        return database.get(username, encryptedPassword);
    }
}
