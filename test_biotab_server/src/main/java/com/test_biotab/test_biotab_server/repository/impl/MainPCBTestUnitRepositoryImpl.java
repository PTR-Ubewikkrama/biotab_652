package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.MainPCBTestUnit;
import com.test_biotab.test_biotab_server.repository.MainPCBTestUnitRepository;
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
public class MainPCBTestUnitRepositoryImpl implements MainPCBTestUnitRepository
{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MainPCBTestUnit save(MainPCBTestUnit MainPCBTestUnit) {
        log.info("Saving MainPCBTestUnit: {}", MainPCBTestUnit.getMainTestId());
        return entityManager.merge(MainPCBTestUnit);
    }


    @Override
    public List<MainPCBTestUnit> findByCustomQuery(TypedQuery<MainPCBTestUnit> query, int pageNo) {
        log.info("Finding MainPCBTestUnit by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<MainPCBTestUnit> findByCustomQuery(TypedQuery<MainPCBTestUnit> query) {
        log.info("Finding MainPCBTestUnit by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting MainPCBTestUnit by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public List<MainPCBTestUnit> getMainPCBTestUnitByMainTestId(int mainTestId) {
        log.info("Finding MainPCBTestUnit by mainTestId: {}", mainTestId);
        return entityManager.createQuery("SELECT m FROM MainPCBTestUnit m WHERE m.mainTestId = :mainTestId", MainPCBTestUnit.class)
                .setParameter("mainTestId", mainTestId)
                .getResultList();
    }
}
