package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.HHDevice;
import com.test_biotab.test_biotab_server.repository.HHDeviceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
@Slf4j
public class HHDeviceRepositoryImpl implements HHDeviceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public HHDevice findByCode(String code) {
        log.info("Finding HHDevice by code: {}", code);
        String jpql = "SELECT d FROM HHDevice d WHERE d.deviceCode = :code";
        TypedQuery<HHDevice> query = entityManager.createQuery(jpql, HHDevice.class);
        query.setParameter("code", code);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public HHDevice save(HHDevice hhDevice) {
        log.info("Saving HHDevice: {}", hhDevice.getDeviceId());
        return entityManager.merge(hhDevice);
    }

    @Override
    public List<HHDevice> findByCustomQuery(TypedQuery<HHDevice> query, int pageNo) {
        log.info("Finding HHDevice by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<HHDevice> findByCustomQuery(TypedQuery<HHDevice> query) {
        log.info("Finding HHDevice by custom query without pagination: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting HHDevice by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteByCode(String code) {
        log.info("Deleting HHDevice by code: {}", code);
        String jpql = "DELETE FROM HHDevice d WHERE d.deviceCode = :code";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("code", code);
        query.executeUpdate();
    }
}
