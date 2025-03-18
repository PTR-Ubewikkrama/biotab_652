package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.PowerSupplyV2TestData;
import com.test_biotab.test_biotab_server.repository.PowerSupplyV2TestRepository;
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
public class PowerSupplyV2TestRepositoryImpl implements PowerSupplyV2TestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PowerSupplyV2TestData save(PowerSupplyV2TestData airPumpTestData) {
        log.info("Saving PowerSupplyV2TestData: {}", airPumpTestData.getTestId());
        return entityManager.merge(airPumpTestData);
    }

    @Override
    public List<PowerSupplyV2TestData> findByCustomQuery(TypedQuery<PowerSupplyV2TestData> query, int pageNo) {
        log.info("Finding PowerSupplyV2TestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<PowerSupplyV2TestData> findByCustomQuery(TypedQuery<PowerSupplyV2TestData> query) {
        log.info("Finding PowerSupplyV2TestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting PowerSupplyV2TestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public PowerSupplyV2TestData findVerifiedByCode(String code) {
        log.info("Finding PowerSupplyV2TestData by code: {}", code);
        TypedQuery<PowerSupplyV2TestData> query = entityManager.createQuery("SELECT v FROM PowerSupplyV2TestData v WHERE v.serialNumber = :code AND v.status = :status", PowerSupplyV2TestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding PowerSupplyV2TestData by code: {}", code, e);
            return null;
        }
    }

    @Override
    public PowerSupplyV2TestData findByCode(String code) {
        log.info("Finding PowerSupplyV2TestData by code: {}", code);
        TypedQuery<PowerSupplyV2TestData> query = entityManager.createQuery("SELECT v FROM PowerSupplyV2TestData v WHERE v.serialNumber = :code", PowerSupplyV2TestData.class);
        query.setParameter("code", code);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding PowerSupplyV2TestData by code: {}", code, e);
            return null;
        }
    }
}
