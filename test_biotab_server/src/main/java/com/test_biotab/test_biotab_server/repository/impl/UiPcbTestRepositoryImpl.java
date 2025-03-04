package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.UiPcbTestData;
import com.test_biotab.test_biotab_server.repository.UiPcbTestRepository;
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
public class UiPcbTestRepositoryImpl implements UiPcbTestRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public UiPcbTestData save(UiPcbTestData uiPcbTestData) {
        log.info("Saving UiPcbTestData: {}", uiPcbTestData.getTestId());
        return entityManager.merge(uiPcbTestData);
    }

    @Override
    public List<UiPcbTestData> findByCustomQuery(TypedQuery<UiPcbTestData> query, int pageNo) {
        log.info("Finding UiPcbTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<UiPcbTestData> findByCustomQuery(TypedQuery<UiPcbTestData> query) {
        log.info("Finding UiPcbTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting UiPcbTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public UiPcbTestData findByCode(String code) {
        log.info("Finding UiPcbTestData by code: {}", code);
        TypedQuery<UiPcbTestData> query = entityManager.createQuery("SELECT v FROM UiPcbTestData v WHERE v.serialNumber = :code AND v.status = :status", UiPcbTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding UiPcbTestData by code: {}", code, e);
        }
        return null;
    }
}
