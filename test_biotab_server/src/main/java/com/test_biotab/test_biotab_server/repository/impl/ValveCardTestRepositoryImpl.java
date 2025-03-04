package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.ValveCardTestData;
import com.test_biotab.test_biotab_server.repository.ValveCardTestRepository;
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
public class ValveCardTestRepositoryImpl implements ValveCardTestRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public ValveCardTestData save(ValveCardTestData valveCardTestData) {
        log.info("Saving ValveCardTestData: {}", valveCardTestData.getTestId());
        return entityManager.merge(valveCardTestData);
    }

    @Override
    public List<ValveCardTestData> findByCustomQuery(TypedQuery<ValveCardTestData> query, int pageNo) {
        log.info("Finding ValveCardTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<ValveCardTestData> findByCustomQuery(TypedQuery<ValveCardTestData> query) {
        log.info("Finding ValveCardTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting ValveCardTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public ValveCardTestData findByCode(String code) {
        log.info("Finding ValveCardTestData by code: {}", code);
        TypedQuery<ValveCardTestData> query = entityManager.createQuery("SELECT v FROM ValveCardTestData v WHERE v.serialNumber = :code AND v.status = :status", ValveCardTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding ValveCardTestData by code: {}", code, e);
            return null;
        }
    }
}
