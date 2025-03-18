package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.MainPCBTestData;
import com.test_biotab.test_biotab_server.repository.MainPCBTestRepository;
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
public class MainPCBTestRepositoryImpl implements MainPCBTestRepository
{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MainPCBTestData save(MainPCBTestData MainPCBTestData) {
        log.info("Saving MainPCBTestData: {}", MainPCBTestData.getTestId());
        return entityManager.merge(MainPCBTestData);
    }


    @Override
    public List<MainPCBTestData> findByCustomQuery(TypedQuery<MainPCBTestData> query, int pageNo) {
        log.info("Finding MainPCBTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<MainPCBTestData> findByCustomQuery(TypedQuery<MainPCBTestData> query) {
        log.info("Finding MainPCBTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting MainPCBTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public MainPCBTestData findVerifiedByCode(String code) {
        log.info("Finding MainPCBTestData by code: {}", code);
        TypedQuery<MainPCBTestData> query = entityManager.createQuery("SELECT v FROM MainPCBTestData v WHERE v.serialNumber = :code AND v.status = :status", MainPCBTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding MainPCBTestData by code: {}", code, e);
            return null;
        }
    }

    @Override
    public MainPCBTestData findByCode(String code) {
        log.info("Finding MainPCBTestData by code: {}", code);
        TypedQuery<MainPCBTestData> query = entityManager.createQuery("SELECT v FROM MainPCBTestData v WHERE v.serialNumber = :code", MainPCBTestData.class);
        query.setParameter("code", code);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding MainPCBTestData by code: {}", code, e);
            return null;
        }
    }
}
