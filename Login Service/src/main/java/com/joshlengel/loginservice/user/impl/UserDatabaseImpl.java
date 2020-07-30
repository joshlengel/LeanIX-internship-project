package com.joshlengel.loginservice.user.impl;

import com.joshlengel.loginservice.user.User;
import com.joshlengel.loginservice.user.UserDatabase;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserDatabaseImpl implements UserDatabase {

    private final List<User> database = new ArrayList<>();

    @Override
    public Optional<User> add(String username, String encryptedPassword) {
        if (database.stream().anyMatch(user ->
                user.getUsername().equals(username)
                && user.getEncryptedPassword().equals(encryptedPassword))) {
            return Optional.empty();
        }

        User user = new User(username, encryptedPassword);
        database.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> get(String username, String encryptedPassword) {
        for (User user : database) {
            if (user.getUsername().equals(username)
                && user.getEncryptedPassword().equals(encryptedPassword)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
