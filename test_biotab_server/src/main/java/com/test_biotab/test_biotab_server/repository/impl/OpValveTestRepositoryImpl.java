package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.OpValveTestData;
import com.test_biotab.test_biotab_server.repository.OpValveTestRepository;
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
public class OpValveTestRepositoryImpl implements OpValveTestRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OpValveTestData save(OpValveTestData opValveTestData) {
        log.info("Saving OpValveTestData: {}", opValveTestData.getTestId());
        return entityManager.merge(opValveTestData);
    }

    @Override
    public List<OpValveTestData> findByCustomQuery(TypedQuery<OpValveTestData> query, int pageNo) {
        log.info("Finding OpValveTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<OpValveTestData> findByCustomQuery(TypedQuery<OpValveTestData> query) {
        log.info("Finding OpValveTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting OpValveTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public OpValveTestData findVerifiedByCode(String code) {
        log.info("Finding OpValveTestData by code: {}", code);
        TypedQuery<OpValveTestData> query = entityManager.createQuery("SELECT v FROM OpValveTestData v WHERE v.serialNumber = :code AND v.status = :status", OpValveTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding OpValveTestData by code: {}", code, e);
        }
        return null;
    }

    @Override
    public OpValveTestData findByCode(String code) {
        log.info("Finding OpValveTestData by code: {}", code);
        TypedQuery<OpValveTestData> query = entityManager.createQuery("SELECT v FROM OpValveTestData v WHERE v.serialNumber = :code", OpValveTestData.class);
        query.setParameter("code", code);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding OpValveTestData by code: {}", code, e);
        }
        return null;
    }
}
