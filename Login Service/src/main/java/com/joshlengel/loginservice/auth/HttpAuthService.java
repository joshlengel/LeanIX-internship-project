package com.joshlengel.loginservice.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class HttpAuthService {

    @Inject
    AuthService authService;

    @Inject
    PermissionValidatorService pValService;

    public void authenticate(HttpHeaders headers, ApiToken token, String username) throws WebApplicationException {
        String authHeader = headers.getHeaderString("Authorization");

        Jws<Claims> claims = authService.validateToken(authHeader);

        if (!username.equals(claims.getBody().getSubject()))
            throw new WebApplicationException("Invalid token", Response.Status.UNAUTHORIZED);

        @SuppressWarnings("unchecked")
        List<String> roles = claims.getBody().get("roles", List.class);

        pValService.checkPermission(roles, token);
    }

    public void authenticate(HttpHeaders headers, ApiToken token) throws WebApplicationException {
        String authHeader = headers.getHeaderString("Authorization");

        Jws<Claims> claims = authService.validateToken(authHeader);

        @SuppressWarnings("unchecked")
        List<String> roles = claims.getBody().get("roles", List.class);

        pValService.checkPermission(roles, token);
    }
}
