package com.joshlengel.loginservice;

import com.joshlengel.encryptionservice.resources.EncryptionResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class LoginTest {

    @Test
    public void testLogin() {
        EncryptionResource encryptionResource = new EncryptionResource();

        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String encryptedPassword = encryptionResource.encrypt(password);

        given()
            .when()
                .pathParam("login", "/new")
                .formParam("username", username)
                .formParam("password", password)
            .then()
                .statusCode(200)
                .body(is("{\"encryptedPasswrd\":\"" + encryptedPassword + "\",\"id\":\"" + 1 + "\",\"username\":\"" + username + "\"}"));

        given()
            .when()
                .pathParam("login", "")
                .formParam("username", username)
                .formParam("password", password)
            .then()
                .statusCode(200)
                .body(is("{\"encryptedPasswrd\":\"" + encryptedPassword + "\",\"id\":\"" + 1 + "\",\"username\":\"" + username + "\"}"));
    }
}
