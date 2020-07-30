package com.joshlengel.loginservice.user;

import javax.persistence.*;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "encryptedPassword", nullable = false)
    private String encryptedPassword;
    
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEncryptedPassword() { return encryptedPassword; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEncryptedPassword(String encryptedPassword) { this.encryptedPassword = encryptedPassword; }
}
