package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.PowerSupplyTestData;
import com.test_biotab.test_biotab_server.repository.PowerSupplyTestRepository;
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
public class PowerSupplyTestRepositoryImpl implements PowerSupplyTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PowerSupplyTestData save(PowerSupplyTestData PowerSupplyTestData) {
        log.info("Saving PowerSupplyTestData: {}", PowerSupplyTestData.getTestId());
        return entityManager.merge(PowerSupplyTestData);
    }


    @Override
    public List<PowerSupplyTestData> findByCustomQuery(TypedQuery<PowerSupplyTestData> query, int pageNo) {
        log.info("Finding PowerSupplyTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<PowerSupplyTestData> findByCustomQuery(TypedQuery<PowerSupplyTestData> query) {
        log.info("Finding PowerSupplyTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting PowerSupplyTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public PowerSupplyTestData findByCode(String code) {
        log.info("Finding PowerSupplyTestData by code: {}", code);
        TypedQuery<PowerSupplyTestData> query = entityManager.createQuery("SELECT v FROM PowerSupplyTestData v WHERE v.serialNumber = :code AND v.status = :status", PowerSupplyTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
