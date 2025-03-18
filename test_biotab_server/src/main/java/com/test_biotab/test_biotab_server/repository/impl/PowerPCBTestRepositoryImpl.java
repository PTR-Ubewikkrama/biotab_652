package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.PowerPCBTestData;
import com.test_biotab.test_biotab_server.repository.PowerPCBTestRepository;
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
public class PowerPCBTestRepositoryImpl implements PowerPCBTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PowerPCBTestData save(PowerPCBTestData powerPCBTestData) {
        log.info("Saving PowerPCBTestData: {}", powerPCBTestData.getTestId());
        return entityManager.merge(powerPCBTestData);
    }

    @Override
    public List<PowerPCBTestData> findByCustomQuery(TypedQuery<PowerPCBTestData> query, int pageNo) {
        log.info("Finding PowerPCBTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<PowerPCBTestData> findByCustomQuery(TypedQuery<PowerPCBTestData> query) {
        log.info("Finding PowerPCBTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting PowerPCBTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public PowerPCBTestData findVerifiedByCode(String code) {
        log.info("Finding PowerPCBTestData by code: {}", code);
        TypedQuery<PowerPCBTestData> query = entityManager.createQuery("SELECT v FROM PowerPCBTestData v WHERE v.serialNumber = :code AND v.status = :status", PowerPCBTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding PowerPCBTestData by code: {}", code, e);
            return null;
        }
    }

    @Override
    public PowerPCBTestData findByCode(String code) {
        log.info("Finding PowerPCBTestData by code: {}", code);
        TypedQuery<PowerPCBTestData> query = entityManager.createQuery("SELECT v FROM PowerPCBTestData v WHERE v.serialNumber = :code", PowerPCBTestData.class);
        query.setParameter("code", code);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding PowerPCBTestData by code: {}", code, e);
            return null;
        }
    }
}
