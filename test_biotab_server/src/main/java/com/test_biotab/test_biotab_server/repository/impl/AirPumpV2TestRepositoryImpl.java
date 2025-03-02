package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.AirPumpV2TestData;
import com.test_biotab.test_biotab_server.repository.AirPumpV2TestRepository;
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
public class AirPumpV2TestRepositoryImpl implements AirPumpV2TestRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public AirPumpV2TestData save(AirPumpV2TestData airPumpV2TestData) {
        log.info("Saving AirPumpV2TestData: {}", airPumpV2TestData.getTestId());
        return entityManager.merge(airPumpV2TestData);
    }

    @Override
    public List<AirPumpV2TestData> findByCustomQuery(TypedQuery<AirPumpV2TestData> query, int pageNo) {
        log.info("Finding AirPumpV2TestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<AirPumpV2TestData> findByCustomQuery(TypedQuery<AirPumpV2TestData> query) {
        log.info("Finding AirPumpV2TestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting AirPumpV2TestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public AirPumpV2TestData findByCode(String code) {
        log.info("Finding AirPumpV2TestData by code: {}", code);
        TypedQuery<AirPumpV2TestData> query = entityManager.createQuery("SELECT v FROM AirPumpV2TestData v WHERE v.serialNumber = :code AND v.status = :status", AirPumpV2TestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding AirPumpV2TestData by code: {}", code, e);
            return null;
        }
    }
}
