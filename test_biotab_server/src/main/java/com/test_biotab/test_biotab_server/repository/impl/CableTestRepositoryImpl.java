package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.CableTestData;
import com.test_biotab.test_biotab_server.repository.CableTestRepository;
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
public class CableTestRepositoryImpl implements CableTestRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public CableTestData save(CableTestData cableTestData) {
        log.info("Saving CableTestData: {}", cableTestData.getTestId());
        return entityManager.merge(cableTestData);
    }

    @Override
    public List<CableTestData> findByCustomQuery(TypedQuery<CableTestData> query, int page) {
        log.info("Finding CableTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(page * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<CableTestData> findByCustomQuery(TypedQuery<CableTestData> query) {
        log.info("Finding CableTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting CableTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public CableTestData findByCode(String code) {
        log.info("Finding CableTestData by code: {}", code);
        TypedQuery<CableTestData> query = entityManager.createQuery("SELECT v FROM CableTestData v WHERE v.serialNumber = :code AND v.status = :status", CableTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding CableTestData by code: {}", code, e);
            return null;
        }
    }
}
