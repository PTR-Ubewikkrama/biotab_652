package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.AirPumpTestData;
import com.test_biotab.test_biotab_server.repository.AirPumpTestRepository;
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
public class AirPumpTestRepositoryImpl implements AirPumpTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AirPumpTestData save(AirPumpTestData AirPumpTestData) {
        log.info("Saving AirPumpTestData: {}", AirPumpTestData.getTestId());
        return entityManager.merge(AirPumpTestData);
    }


    @Override
    public List<AirPumpTestData> findByCustomQuery(TypedQuery<AirPumpTestData> query, int pageNo) {
        log.info("Finding AirPumpTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<AirPumpTestData> findByCustomQuery(TypedQuery<AirPumpTestData> query) {
        log.info("Finding AirPumpTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting AirPumpTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public AirPumpTestData findVerifiedByCode(String code) {
        log.info("Finding AirPumpTestData by code: {}", code);
        TypedQuery<AirPumpTestData> query = entityManager.createQuery("SELECT v FROM AirPumpTestData v WHERE v.serialNumber = :code AND v.status = :status", AirPumpTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding AirPumpTestData by code: {}", code, e);
            return null;
        }
    }

    @Override
    public AirPumpTestData findByCode(String code) {
        log.info("Finding AirPumpTestData by code: {}", code);
        TypedQuery<AirPumpTestData> query = entityManager.createQuery("SELECT v FROM AirPumpTestData v WHERE v.serialNumber = :code", AirPumpTestData.class);
        query.setParameter("code", code);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding AirPumpTestData by code: {}", code, e);
            return null;
        }
    }
}
