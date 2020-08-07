package com.joshlengel.loginservice.db;

import com.joshlengel.loginservice.LoginCredentials;
import org.hibernate.exception.ConstraintViolationException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<UserAccount> cq = cb.createQuery(UserAccount.class);
        Root<UserAccount> cr = cq.from(UserAccount.class);

        TypedQuery<UserAccount> query = manager.createQuery(cq
                .select(cr)
                        .where(
                                cb.and(
                                        cb.equal(cr.get("username"), credentials.getUsername()),
                                        cb.equal(cr.get("password"), credentials.getPassword())
                                )
                        ));

        UserAccount account = query.getResultStream()
                .findFirst()
                .orElseThrow(() -> new WebApplicationException("Invalid credentials", Response.Status.UNAUTHORIZED));

        manager.remove(account);
    }

    @Transactional
    public Optional<UserAccount> find(LoginCredentials credentials) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<UserAccount> cq = cb.createQuery(UserAccount.class);
        Root<UserAccount> cr = cq.from(UserAccount.class);

        TypedQuery<UserAccount> query = manager.createQuery(cq
                .select(cr)
                        .where(
                                cb.and(
                                        cb.equal(cr.get("username"), credentials.getUsername()),
                                        cb.equal(cr.get("password"), credentials.getPassword())
                                )
                        )
                );

        return query.getResultStream().findFirst();
    }

    @Transactional
    public void updateRoles(String username, String roles) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaUpdate<UserAccount> cu = cb.createCriteriaUpdate(UserAccount.class);
        Root<UserAccount> cr = cu.from(UserAccount.class);

        Query query = manager.createQuery(cu
                .set(cr.get("roles"), roles)
                        .where(
                                cb.equal(cr.get("username"), username)
                        )
                );

        query.executeUpdate();
    }

    @Transactional
    public void dropTable() {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaDelete<UserAccount> cd = cb.createCriteriaDelete(UserAccount.class);
        Root<UserAccount> cr = cd.from(UserAccount.class);

        Query query = manager.createQuery(cd
            .where(
                    cb.notLike(cr.get("roles"), "%sysadmin%")
            ));

        query.executeUpdate();
    }
}
