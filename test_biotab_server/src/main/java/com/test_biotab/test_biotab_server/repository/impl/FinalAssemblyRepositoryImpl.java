package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.FinalAssembly;
import com.test_biotab.test_biotab_server.repository.FinalAssemblyRepository;
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
public class FinalAssemblyRepositoryImpl implements FinalAssemblyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FinalAssembly findByDeviceId(String id) {
        log.info("Finding FinalAssembly by device code: {}", id);
        String jpql = "SELECT d FROM FinalAssembly d WHERE d.deviceCode = :id";
        TypedQuery<FinalAssembly> query = entityManager.createQuery(jpql, FinalAssembly.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public FinalAssembly save(FinalAssembly finalAssembly) {
        log.info("Saving FinalAssembly: {}", finalAssembly.getDeviceCode());
        return entityManager.merge(finalAssembly);
    }


    @Override
    public List<FinalAssembly> findByCustomQuery(TypedQuery<FinalAssembly> query, int pageNo) {
        log.info("Finding FinalAssembly by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<FinalAssembly> findByCustomQuery(TypedQuery<FinalAssembly> query) {
        log.info("Finding FinalAssembly by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting FinalAssembly by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public void deleteById(int l) {
        FinalAssembly FinalAssembly = entityManager.find(FinalAssembly.class, l);
        entityManager.remove(FinalAssembly);
    }

    @Override
    public FinalAssembly findByCustomQueryForSingle(TypedQuery<FinalAssembly> query) {
        log.info("Finding FinalAssembly by custom query: {}", query.toString());
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
