package com.joshlengel.encryptionservice;

import com.joshlengel.encryptionservice.impl.EncryptionServiceImpl;
import org.junit.Assert;
import org.junit.Test;

public class TestEncryption {

    @Test
    public void testEncryptionService() {
        EncryptionService service = new EncryptionServiceImpl();

        String message = "abcdefghijklmnop";
        String encryptedTarget = "klmnopqrstuvwxyz";
        String encrypted = service.encrypt(message);

        Assert.assertEquals(encrypted, encryptedTarget);
    }
}
