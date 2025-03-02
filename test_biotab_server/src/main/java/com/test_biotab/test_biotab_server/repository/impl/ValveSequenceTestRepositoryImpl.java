package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.ValveSequenceTestData;
import com.test_biotab.test_biotab_server.repository.ValveSequenceTestRepository;
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
public class ValveSequenceTestRepositoryImpl implements ValveSequenceTestRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public ValveSequenceTestData save(ValveSequenceTestData valveSequenceTestData) {
        log.info("Saving ValveSequenceTestData: {}", valveSequenceTestData.getTestId());
        return entityManager.merge(valveSequenceTestData);
    }

    @Override
    public List<ValveSequenceTestData> findByCustomQuery(TypedQuery<ValveSequenceTestData> query, int pageNo) {
        log.info("Finding ValveSequenceTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<ValveSequenceTestData> findByCustomQuery(TypedQuery<ValveSequenceTestData> query) {
        log.info("Finding ValveSequenceTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting ValveSequenceTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public ValveSequenceTestData findByCode(String code) {
        log.info("Finding ValveSequenceTestData by code: {}", code);
        TypedQuery<ValveSequenceTestData> query = entityManager.createQuery("SELECT v FROM ValveSequenceTestData v WHERE v.qrCode = :code AND v.status = :status", ValveSequenceTestData.class);
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
