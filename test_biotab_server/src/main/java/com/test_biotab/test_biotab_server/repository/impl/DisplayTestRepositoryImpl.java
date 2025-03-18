package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.DisplayTestData;
import com.test_biotab.test_biotab_server.repository.DisplayTestRepository;
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
public class DisplayTestRepositoryImpl implements DisplayTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DisplayTestData save(DisplayTestData displayTestData) {
        log.info("Saving DisplayTestData: {}", displayTestData.getTestId());
        return entityManager.merge(displayTestData);
    }

    @Override
    public List<DisplayTestData> findByCustomQuery(TypedQuery<DisplayTestData> query, int pageNo) {
        log.info("Finding DisplayTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<DisplayTestData> findByCustomQuery(TypedQuery<DisplayTestData> query) {
        log.info("Finding DisplayTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting DisplayTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public DisplayTestData findVerifiedByCode(String code) {
        log.info("Finding DisplayTestData by code: {}", code);
        TypedQuery<DisplayTestData> query = entityManager.createQuery("SELECT v FROM DisplayTestData v WHERE v.serialNumber = :code AND v.status = :status", DisplayTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public DisplayTestData findByCode(String code) {
        log.info("Finding DisplayTestData by code: {}", code);
        TypedQuery<DisplayTestData> query = entityManager.createQuery("SELECT v FROM DisplayTestData v WHERE v.serialNumber = :code", DisplayTestData.class);
        query.setParameter("code", code);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
