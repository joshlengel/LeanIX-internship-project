package com.joshlengel.loginservice.user;

import java.util.Optional;

public interface UserDatabase {
    Optional<User> add(String username, String encryptedPassword);
    Optional<User> get(String username, String encryptedPassword);
}
