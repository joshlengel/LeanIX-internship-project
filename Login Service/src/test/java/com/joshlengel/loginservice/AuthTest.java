package com.joshlengel.loginservice;

import com.joshlengel.loginservice.auth.AuthService;
import com.joshlengel.loginservice.db.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

@QuarkusTest
public class AuthTest {

    @Inject
    AuthService service;

    @Test
    public void testAuth() throws InterruptedException {
        UserAccount account = new UserAccount();
        account.username = "testUsername";
        account.password = "testPassword";
        account.roles = "admin, sysadmin";

        String token = service.getToken(account);

        Jws<Claims> claims = service.validateToken(token);

        Assertions.assertEquals(account.username, claims.getBody().getSubject());
        Assertions.assertEquals(account.getRoles(), claims.getBody().get("roles", List.class));

        Thread.sleep(1100);

        WebApplicationException exception =
                Assertions.assertThrows(WebApplicationException.class, () -> service.validateToken(token));

        Assertions.assertEquals(exception.getResponse().getStatus(), Response.Status.GATEWAY_TIMEOUT.getStatusCode());
    }
}
