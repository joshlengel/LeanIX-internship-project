package com.joshlengel.loginservice.user;

public interface UserDatabase {
    User add(String username, String encryptedPassword);
    User get(String username, String encryptedPassword);
}
