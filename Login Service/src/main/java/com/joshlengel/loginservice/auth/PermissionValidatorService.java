package com.joshlengel.loginservice.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class PermissionValidatorService {

    public void checkPermission(List<String> roles, ApiToken api) throws WebApplicationException {
        if (api.getRoles().stream().noneMatch(roles::contains)) {
            throw new WebApplicationException("Access forbidden", Response.Status.FORBIDDEN);
        }
    }
}
