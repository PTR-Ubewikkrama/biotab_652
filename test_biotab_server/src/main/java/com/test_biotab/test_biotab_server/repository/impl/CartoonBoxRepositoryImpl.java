package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.CartoonBox;
import com.test_biotab.test_biotab_server.repository.CartoonBoxRepository;
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
public class CartoonBoxRepositoryImpl implements CartoonBoxRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CartoonBox findByCartoonNumber(String cartoonNumber) {
        log.info("Finding CartoonBox by cartoonNumber: {}", cartoonNumber);
        TypedQuery<CartoonBox> query = entityManager.createQuery("SELECT c FROM CartoonBox c WHERE c.cartoonNumber = :cartoonNumber", CartoonBox.class);
        query.setParameter("cartoonNumber", cartoonNumber);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CartoonBox save(CartoonBox finalAssembly) {
        log.info("Saving CartoonBox: {}", finalAssembly.getCartoonNumber());
        return entityManager.merge(finalAssembly);
    }


    @Override
    public List<CartoonBox> findByCustomQuery(TypedQuery<CartoonBox> query, int pageNo) {
        log.info("Finding CartoonBox by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<CartoonBox> findByCustomQuery(TypedQuery<CartoonBox> query) {
        log.info("Finding CartoonBox by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting CartoonBox by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public void deleteById(int l) {
        CartoonBox CartoonBox = entityManager.find(CartoonBox.class, l);
        entityManager.remove(CartoonBox);
    }

    @Override
    public CartoonBox findByCustomQueryForSingle(TypedQuery<CartoonBox> query) {
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
