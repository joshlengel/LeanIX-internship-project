package com.joshlengel.encryptionservice.impl;

import com.joshlengel.encryptionservice.EncryptionService;

public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String encrypt(String message) {
        char[] result = message.toCharArray();

        for (int i = 0; i < result.length; ++i) {
            result[i] += 10;
        }

        return new String(result);
    }
}
