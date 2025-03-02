package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.FanTestData;
import com.test_biotab.test_biotab_server.repository.FanTestRepository;
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
public class FanTestRepositoryImpl implements FanTestRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FanTestData save(FanTestData fanTestData) {
        log.info("Saving FanTestData: {}", fanTestData.getTestId());
        return entityManager.merge(fanTestData);
    }

    @Override
    public List<FanTestData> findByCustomQuery(TypedQuery<FanTestData> query, int page) {
        log.info("Finding FanTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(page * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<FanTestData> findByCustomQuery(TypedQuery<FanTestData> query) {
        log.info("Finding FanTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting FanTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public FanTestData findByCode(String code) {
        log.info("Finding FanTestData by code: {}", code);
        TypedQuery<FanTestData> query = entityManager.createQuery("SELECT v FROM FanTestData v WHERE v.qrCode = :code AND v.status = :status", FanTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding FanTestData by code: {}", code, e);
            return null;
        }
    }
}
