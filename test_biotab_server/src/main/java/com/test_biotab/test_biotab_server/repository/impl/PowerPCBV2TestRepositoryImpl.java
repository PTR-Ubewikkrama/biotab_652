package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.PowerPCBV2TestData;
import com.test_biotab.test_biotab_server.repository.PowerPCBV2TestRepository;
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
public class PowerPCBV2TestRepositoryImpl implements PowerPCBV2TestRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public PowerPCBV2TestData save(PowerPCBV2TestData powerPCBV2TestData) {
        log.info("Saving PowerPCBV2TestData: {}", powerPCBV2TestData.getTestId());
        return entityManager.merge(powerPCBV2TestData);
    }

    @Override
    public List<PowerPCBV2TestData> findByCustomQuery(TypedQuery<PowerPCBV2TestData> query, int pageNo) {
        log.info("Finding PowerPCBV2TestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<PowerPCBV2TestData> findByCustomQuery(TypedQuery<PowerPCBV2TestData> query) {
        log.info("Finding PowerPCBV2TestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting PowerPCBV2TestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public PowerPCBV2TestData findByCode(String code) {
        log.info("Finding PowerPCBV2TestData by code: {}", code);
        TypedQuery<PowerPCBV2TestData> query = entityManager.createQuery("SELECT v FROM PowerPCBV2TestData v WHERE v.serialNumber = :code AND v.status = :status", PowerPCBV2TestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding PowerPCBV2TestData by code: {}", code, e);
        }
        return null;
    }
}
