package com.test_jig.test_jig_server.repository.impl;

import com.test_jig.test_jig_server.entity.PcbTestData;
import com.test_jig.test_jig_server.repository.PcbTestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@Slf4j
public class PcbTestRepositoryImpl implements PcbTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PcbTestData save(PcbTestData PcbTestData) {
        log.info("Saving PcbTestData: {}", PcbTestData.getTestId());
        return entityManager.merge(PcbTestData);
    }


    @Override
    public List<PcbTestData> findByCustomQuery(TypedQuery<PcbTestData> query, int pageNo) {
        log.info("Finding PcbTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<PcbTestData> findByCustomQuery(TypedQuery<PcbTestData> query) {
        log.info("Finding PcbTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting PcbTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public void deleteById(int l) {
        PcbTestData PcbTestData = entityManager.find(PcbTestData.class, l);
        entityManager.remove(PcbTestData);
    }

    @Override
    public PcbTestData findByCode(String code) {
        log.info("Finding PcbTestData by code: {}", code);
        TypedQuery<PcbTestData> query = entityManager.createQuery(
                "SELECT p FROM PcbTestData p WHERE p.serialNumber = :code AND p.status = :status",
                PcbTestData.class
        );
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
