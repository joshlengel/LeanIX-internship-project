package com.joshlengel.loginservice.db;

import com.joshlengel.loginservice.LoginCredentials;
import org.hibernate.exception.ConstraintViolationException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Singleton
public class UserAccountDAO {

    @Inject
    EntityManager manager;

    @Transactional
    public void create(UserAccount account) throws WebApplicationException {
        try {
            manager.persist(account);
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new WebApplicationException("Username already exists", Response.Status.BAD_REQUEST);
            } else {
                throw e;
            }
        }
    }

    @Transactional
    public void remove(LoginCredentials credentials) throws WebApplicationException {
        UserAccount account =
                manager.createQuery("SELECT ua FROM UserAccount ua WHERE username=:username AND password=:password", UserAccount.class)
                .setParameter("username", credentials.getUsername())
                .setParameter("password", credentials.getPassword())
                .getResultStream().findFirst()
                .orElseThrow(() -> new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED));

        manager.remove(account);
    }

    @Transactional
    public Optional<UserAccount> find(LoginCredentials credentials) {
        return manager.createQuery("SELECT ua FROM UserAccount ua WHERE username=:username AND password=:password", UserAccount.class)
                .setParameter("username", credentials.getUsername())
                .setParameter("password", credentials.getPassword())
                .getResultStream().findFirst();
    }
}
