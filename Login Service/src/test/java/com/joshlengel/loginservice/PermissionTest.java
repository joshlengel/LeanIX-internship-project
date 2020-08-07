package com.joshlengel.loginservice;

import com.joshlengel.loginservice.auth.ApiToken;
import com.joshlengel.loginservice.auth.PermissionValidatorService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.Arrays;
import java.util.List;

@QuarkusTest
public class PermissionTest {

    @Inject
    PermissionValidatorService pValService;

    @Test
    public void testPermissions() {
        List<String> roles1 = Arrays.asList("user", "admin", "sysadmin");
        pValService.checkPermission(roles1, ApiToken.DROP_USER_TABLE);
        pValService.checkPermission(roles1, ApiToken.PROMOTE_USER);

        List<String> roles2 = Arrays.asList("user", "admin");
        pValService.checkPermission(roles2, ApiToken.ADD_USER);
        pValService.checkPermission(roles2, ApiToken.REMOVE_USER);

        Assertions.assertThrows(WebApplicationException.class, () -> pValService.checkPermission(roles2, ApiToken.DROP_USER_TABLE));

        List<String> roles3 = Arrays.asList("user");
        pValService.checkPermission(roles3, ApiToken.GET_HIDDEN_DATA);

        Assertions.assertThrows(WebApplicationException.class, () -> pValService.checkPermission(roles3, ApiToken.DROP_USER_TABLE));
        Assertions.assertThrows(WebApplicationException.class, () -> pValService.checkPermission(roles3, ApiToken.ADD_USER));
    }
}
