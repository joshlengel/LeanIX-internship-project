package com.joshlengel.loginservice.user.impl;

import com.joshlengel.loginservice.user.UserAccount;
import com.joshlengel.loginservice.user.UserDatabase;
import org.hibernate.exception.ConstraintViolationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.Optional;

@ApplicationScoped
public class UserDatabaseImpl implements UserDatabase {

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public Optional<UserAccount> add(String username, String encryptedPassword) {
        UserAccount user = new UserAccount();
        user.setUsername(username);
        user.setEncryptedPassword(encryptedPassword);

        try {
            entityManager.persist(user);
            entityManager.flush();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                return Optional.empty();
            } else {
                throw e; // Not the exception we care about
            }
        }

        return Optional.of(user);
    }

    @Override
    @Transactional
    public Optional<UserAccount> get(String username, String encryptedPassword) {
        UserAccount user = new UserAccount();
        user.setUsername(username);
        user.setEncryptedPassword(encryptedPassword);

        Query query = entityManager.createQuery(
                "SELECT ua FROM UserAccount ua WHERE username = :username AND encryptedPassword = :encryptedPassword");

        query.setParameter("username", username);
        query.setParameter("encryptedPassword", encryptedPassword);

        return query.getResultList().size() > 0? Optional.of((UserAccount) query.getSingleResult()) : Optional.empty();
    }
}
