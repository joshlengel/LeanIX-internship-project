package com.joshlengel.loginservice.resources;

import com.joshlengel.loginservice.auth.ApiToken;
import com.joshlengel.loginservice.auth.HttpAuthService;
import com.joshlengel.loginservice.db.UserAccountDAO;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

@Path("api/management")
public class ManageResource {

    @Inject
    UserAccountDAO userAccountDAO;

    @Inject
    HttpAuthService authService;

    @DELETE
    @Path("/drop-accounts")
    public void dropAccounts(@Context HttpHeaders headers) {
        authService.authenticate(headers, ApiToken.DROP_USER_TABLE);

        userAccountDAO.dropTable();
    }
}
