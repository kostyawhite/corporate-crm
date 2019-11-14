package com.sberbank.crm.loginservice.dao;

import com.sberbank.crm.loginservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public User findUserByNameOrEmail(String username, String email) {
        try {
            String statement = "SELECT u FROM User u WHERE u.name = :username OR u.email = :email";
            Query query = entityManager.createQuery(statement, User.class);
            query.setParameter("username", username);
            query.setParameter("email", email);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
