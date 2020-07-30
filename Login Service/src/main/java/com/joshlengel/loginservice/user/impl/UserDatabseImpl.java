package com.joshlengel.loginservice.user.impl;

import com.joshlengel.loginservice.user.User;
import com.joshlengel.loginservice.user.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDatabseImpl implements UserDatabase {

    private final List<User> database = new ArrayList<>();

    @Override
    public User add(String username, String encryptedPassword) {
        if (database.stream().anyMatch(user ->
                user.getUsername().equals(username)
                && user.getEncryptedPassword().equals(encryptedPassword))) {
            return null;
        }

        User user = new User(username, encryptedPassword);
        database.add(user);
        return user;
    }

    @Override
    public User get(String username, String encryptedPassword) {
        for (User user : database) {
            if (user.getUsername().equals(username)
                && user.getEncryptedPassword().equals(encryptedPassword)) {
                return user;
            }
        }

        return null;
    }
}
