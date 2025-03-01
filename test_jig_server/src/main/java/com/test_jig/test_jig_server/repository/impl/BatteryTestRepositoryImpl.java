package com.test_jig.test_jig_server.repository.impl;

import com.test_jig.test_jig_server.entity.BatteryTestData;
import com.test_jig.test_jig_server.repository.BatteryTestRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.List;

@Repository
@Transactional
@Slf4j
public class BatteryTestRepositoryImpl implements BatteryTestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BatteryTestData save(BatteryTestData BatteryTestData) {
        log.info("Saving ValveTestData: {}", BatteryTestData.getTestId());
        return entityManager.merge(BatteryTestData);
    }


    @Override
    public List<BatteryTestData> findByCustomQuery(TypedQuery<BatteryTestData> query, int pageNo) {
        log.info("Finding ValveTestData by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<BatteryTestData> findByCustomQuery(TypedQuery<BatteryTestData> query) {
        log.info("Finding ValveTestData by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting ValveTestData by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public void deleteById(int l) {
        BatteryTestData BatteryTestData = entityManager.find(BatteryTestData.class, l);
        entityManager.remove(BatteryTestData);
    }

    @Override
    public BatteryTestData findByCode(String code) {
        log.info("Finding ValveTestData by code: {}", code);
        TypedQuery<BatteryTestData> query = entityManager.createQuery("SELECT v FROM BatteryTestData v WHERE v.qrCode = :code AND v.status = :status", BatteryTestData.class);
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
