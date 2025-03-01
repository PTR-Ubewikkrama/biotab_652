package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.OverPressureValveTestData;
import com.test_biotab.test_biotab_server.repository.OverPressureValveTestRepository;
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
public class OverPressureValveTestRepositoryImpl implements OverPressureValveTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OverPressureValveTestData save(OverPressureValveTestData OverPressureValveTestData) {
        log.info("Saving OverPressureValveTestData: {}", OverPressureValveTestData.getTestId());
        return entityManager.merge(OverPressureValveTestData);
    }


    @Override
    public List<OverPressureValveTestData> findByCustomQuery(TypedQuery<OverPressureValveTestData> query, int pageNo) {
        log.info("Finding OverPressureValveTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<OverPressureValveTestData> findByCustomQuery(TypedQuery<OverPressureValveTestData> query) {
        log.info("Finding OverPressureValveTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting OverPressureValveTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public void deleteById(int l) {
        OverPressureValveTestData OverPressureValveTestData = entityManager.find(OverPressureValveTestData.class, l);
        entityManager.remove(OverPressureValveTestData);
    }

    @Override
    public OverPressureValveTestData findByCode(String code) {
        log.info("Finding OverPressureValveTestData by code: {}", code);
        TypedQuery<OverPressureValveTestData> query = entityManager.createQuery("SELECT o FROM OverPressureValveTestData o WHERE o.qrCode = :code AND o.status = :status", OverPressureValveTestData.class);
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
