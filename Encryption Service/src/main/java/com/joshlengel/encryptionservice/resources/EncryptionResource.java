package com.joshlengel.encryptionservice.resources;

import com.joshlengel.encryptionservice.EncryptionService;
import com.joshlengel.encryptionservice.impl.EncryptionServiceImpl;

public class EncryptionResource {

    EncryptionService encryptionService = new EncryptionServiceImpl();

    public String encrypt(String message) {
        return encryptionService.encrypt(message);
    }
}
