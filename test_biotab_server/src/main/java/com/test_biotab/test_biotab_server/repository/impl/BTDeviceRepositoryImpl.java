package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.BTDevice;
import com.test_biotab.test_biotab_server.repository.BTDeviceRepository;
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
public class BTDeviceRepositoryImpl implements BTDeviceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BTDevice findByCode(String code) {
        log.info("Finding BTdevice by code: {}", code);
        String jpql = "SELECT d FROM BTDevice d WHERE d.deviceCode = :code";
        TypedQuery<BTDevice> query = entityManager.createQuery(jpql, BTDevice.class);
        query.setParameter("code", code);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BTDevice save(BTDevice btDevice) {
        log.info("Saving BTDevice: {}", btDevice.getDeviceId());
        return entityManager.merge(btDevice);
    }

    @Override
    public List<BTDevice> findByCustomQuery(TypedQuery<BTDevice> query, int pageNo) {
        log.info("Finding BTDevice by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<BTDevice> findByCustomQuery(TypedQuery<BTDevice> query) {
        log.info("Finding BTDevice by custom query without pagination: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting BTDevice by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteByCode(String code) {
        log.info("Deleting BTDevice by code: {}", code);
        String jpql = "DELETE FROM BTdevice d WHERE d.deviceCode = :code";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("code", code);
        query.executeUpdate();
    }
}
