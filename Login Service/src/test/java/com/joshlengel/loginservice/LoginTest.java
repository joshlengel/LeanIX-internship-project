package com.joshlengel.loginservice;

import com.joshlengel.encryptionservice.resources.EncryptionResource;
import com.joshlengel.loginservice.resources.LoginResource;
import com.joshlengel.loginservice.user.UserAccount;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class LoginTest {

    private Response givenAccountCreated(LoginResource.AccountData data) {
        return given()
                    .basePath("login/new")
                    .contentType(ContentType.JSON)
                    .body(data)
                .when()
                    .post();
    }

    @Test
    public void testNewAccountCreation() {
        EncryptionResource encryptionResource = new EncryptionResource();

        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String encryptedPassword = encryptionResource.encrypt(password);

        LoginResource.AccountData data = new LoginResource.AccountData();
        data.setUsername(username);
        data.setPassword(password);

        UserAccount account = givenAccountCreated(data)
            .then()
                .statusCode(200)
            .extract().response()
            .thenReturn()
                .body()
                .as(UserAccount.class);

        Assertions.assertEquals(account.getUsername(), username);
        Assertions.assertEquals(account.getEncryptedPassword(), encryptedPassword);
    }

    @Test
    public void testDuplicateAccountCreation() {
        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        LoginResource.AccountData data = new LoginResource.AccountData();
        data.setUsername(username);
        data.setPassword(password);

        // Create new account. This should work and is tested in above method
        givenAccountCreated(data);

        givenAccountCreated(data)
            .then()
                .statusCode(400)
                .body(is(""));
    }

    @Test
    public void testAccountLogin() {
        EncryptionResource encryptionResource = new EncryptionResource();

        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();
        String encryptedPassword = encryptionResource.encrypt(password);

        LoginResource.AccountData data = new LoginResource.AccountData();
        data.setUsername(username);
        data.setPassword(password);

        givenAccountCreated(data);

        UserAccount account = given()
                .basePath("login")
                .contentType(ContentType.JSON)
                .body(data)
            .when()
                .post()
            .then()
                .statusCode(200)
                .extract().response()
            .thenReturn()
                .as(UserAccount.class);

        Assertions.assertEquals(account.getUsername(), username);
        Assertions.assertEquals(account.getEncryptedPassword(), encryptedPassword);
    }

    @Test
    public void testUnsuccessfulLogin() {
        String username = UUID.randomUUID().toString();
        String password = UUID.randomUUID().toString();

        LoginResource.AccountData data = new LoginResource.AccountData();
        data.setUsername(username);
        data.setPassword(password);

        given()
                .basePath("login")
                .contentType(ContentType.JSON)
                .body(data)
            .when()
                .post()
            .then()
                .statusCode(400)
                .body(is(""));
    }
}
