package com.joshlengel.loginservice.user;

public class User {

    private static int globalID = 0;

    private final int id;
    private final String username;
    private final String encryptedPassword;

    public User(String username, String encryptedPassword) {
        this.id = ++globalID;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEncryptedPassword() { return encryptedPassword; }
}
