package com.test_jig.test_jig_server.repository.impl;

import com.test_jig.test_jig_server.entity.LatchButtonTestData;
import com.test_jig.test_jig_server.repository.LatchButtonTestRepository;
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
public class LatchButtonTestRepositoryImpl implements LatchButtonTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public LatchButtonTestData save(LatchButtonTestData LatchButtonTestData) {
        log.info("Saving LatchButtonTestData: {}", LatchButtonTestData.getTestId());
        return entityManager.merge(LatchButtonTestData);
    }


    @Override
    public List<LatchButtonTestData> findByCustomQuery(TypedQuery<LatchButtonTestData> query, int pageNo) {
        log.info("Finding LatchButtonTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<LatchButtonTestData> findByCustomQuery(TypedQuery<LatchButtonTestData> query) {
        log.info("Finding LatchButtonTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting LatchButtonTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public void deleteById(int l) {
        LatchButtonTestData LatchButtonTestData = entityManager.find(LatchButtonTestData.class, l);
        entityManager.remove(LatchButtonTestData);
    }

    @Override
    public LatchButtonTestData findByCode(String code) {
        log.info("Finding LatchButtonTestData by code: {}", code);
        TypedQuery<LatchButtonTestData> query = entityManager.createQuery("SELECT l FROM LatchButtonTestData l WHERE l.qrCode = :code AND l.status = :status", LatchButtonTestData.class);
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
