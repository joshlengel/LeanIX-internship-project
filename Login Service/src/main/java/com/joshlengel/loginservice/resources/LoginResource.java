package com.joshlengel.loginservice.resources;

import com.joshlengel.encryptionservice.resources.EncryptionResource;
import com.joshlengel.loginservice.user.UserAccount;
import com.joshlengel.loginservice.user.UserDatabase;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

@Path("login")
public class LoginResource {

    @Inject
    UserDatabase database;

    EncryptionResource encryptionResource;

    public static class AccountData {
        String username;
        String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @Inject
    public LoginResource() {
        encryptionResource = new EncryptionResource();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    public Response create(AccountData data) {
        String encryptedPassword = encryptionResource.encrypt(data.password);

        Optional<UserAccount> account = database.add(data.username, encryptedPassword);

        return account.isPresent()? Response.ok(account.get()).build() : Response.status(400).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AccountData data) {
        String encryptedPassword = encryptionResource.encrypt(data.password);

        Optional<UserAccount> account = database.get(data.username, encryptedPassword);

        return account.isPresent()? Response.ok(account.get()).build() : Response.status(400).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/remove")
    public void delete(@FormParam("username") String username, @FormParam("password") String password) {
        String encryptedPassword = encryptionResource.encrypt(password);

        database.remove(username, encryptedPassword);
    }
}
