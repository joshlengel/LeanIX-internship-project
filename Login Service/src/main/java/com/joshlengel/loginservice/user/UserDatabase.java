package com.joshlengel.loginservice.user;

import java.util.Optional;

public interface UserDatabase {
    Optional<UserAccount> add(String username, String encryptedPassword);
    Optional<UserAccount> get(String username, String encryptedPassword);
}
