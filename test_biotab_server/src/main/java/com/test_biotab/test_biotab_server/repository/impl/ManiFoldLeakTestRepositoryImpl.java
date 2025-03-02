package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.ManiFoldLeakTestData;
import com.test_biotab.test_biotab_server.repository.ManiFoldLeakTestRepository;
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
public class ManiFoldLeakTestRepositoryImpl implements ManiFoldLeakTestRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ManiFoldLeakTestData save(ManiFoldLeakTestData maniFoldLeakTestData) {
        log.info("ManiFoldLeakTestRepositoryImpl save");
        return entityManager.merge(maniFoldLeakTestData);
    }

    @Override
    public List<ManiFoldLeakTestData> findByCustomQuery(TypedQuery<ManiFoldLeakTestData> query, int pageNo) {
        log.info("ManiFoldLeakTestRepositoryImpl findByCustomQuery");
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<ManiFoldLeakTestData> findByCustomQuery(TypedQuery<ManiFoldLeakTestData> query) {
        log.info("ManiFoldLeakTestRepositoryImpl findByCustomQuery");
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("ManiFoldLeakTestRepositoryImpl countByCustomQuery");
        return query.getSingleResult();
    }

    @Override
    public ManiFoldLeakTestData findByCode(String code) {
        log.info("ManiFoldLeakTestRepositoryImpl findByCode");
        TypedQuery<ManiFoldLeakTestData> query = entityManager.createQuery("SELECT v FROM ManiFoldLeakTestData v WHERE v.qrCode = :code AND v.status = :status", ManiFoldLeakTestData.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding ManiFoldLeakTestData by code: {}", code, e);
        }
        return null;
    }
}
