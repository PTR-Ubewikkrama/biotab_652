package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.ValveTestData;
import com.test_biotab.test_biotab_server.repository.ValveTestRepository;
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
public class ValveTestRepositoryImpl implements ValveTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ValveTestData save(ValveTestData ValveTestData) {
        log.info("Saving ValveTestData: {}", ValveTestData.getTestId());
        return entityManager.merge(ValveTestData);
    }


    @Override
    public List<ValveTestData> findByCustomQuery(TypedQuery<ValveTestData> query, int pageNo) {
        log.info("Finding ValveTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<ValveTestData> findByCustomQuery(TypedQuery<ValveTestData> query) {
        log.info("Finding ValveTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting ValveTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public ValveTestData findByCode(String code) {
        log.info("Finding ValveTestData by code: {}", code);
        TypedQuery<ValveTestData> query = entityManager.createQuery("SELECT v FROM ValveTestData v WHERE v.serialNumber = :code AND v.status = :status", ValveTestData.class);
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
