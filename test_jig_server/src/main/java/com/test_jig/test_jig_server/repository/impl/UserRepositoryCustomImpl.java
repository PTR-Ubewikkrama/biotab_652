package com.test_jig.test_jig_server.repository.impl;

import com.test_jig.test_jig_server.entity.User;
import com.test_jig.test_jig_server.repository.UserRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@Slf4j
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByEmail(String email) {
        log.info("Finding user by email");
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public User save(User User) {
        log.info("Saving User: {}", User.getName());
        return entityManager.merge(User);
    }


    @Override
    public List<User> findByCustomQuery(TypedQuery<User> query, int pageNo) {
        log.info("Finding User by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<User> findByCustomQuery(TypedQuery<User> query) {
        log.info("Finding User by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting User by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public void deleteById(int l) {
        User User = entityManager.find(User.class, l);
        entityManager.remove(User);
    }
}
