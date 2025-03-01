package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.Device;
import com.test_biotab.test_biotab_server.repository.DeviceRepository;
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
public class DeviceRepositoryImpl implements DeviceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Device findById(int id) {
        log.info("Finding Device by id: {}", id);
        String jpql = "SELECT d FROM Device d WHERE d.deviceId = :id";
        TypedQuery<Device> query = entityManager.createQuery(jpql, Device.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Device findByMac(String mac) {
        log.info("Finding Device by mac: {}", mac);
        String jpql = "SELECT d FROM Device d WHERE d.deviceMac = :mac";
        TypedQuery<Device> query = entityManager.createQuery(jpql, Device.class);
        query.setParameter("mac", mac);
        return query.getSingleResult();
    }

    @Override
    public Device save(Device Device) {
        log.info("Saving Device: {}", Device.getDeviceId());
        return entityManager.merge(Device);
    }


    @Override
    public List<Device> findByCustomQuery(TypedQuery<Device> query, int pageNo) {
        log.info("Finding Device by custom query: {}", query.toString());
        return query
                .setFirstResult(pageNo * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<Device> findByCustomQuery(TypedQuery<Device> query) {
        log.info("Finding Device by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting Device by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public void deleteById(int l) {
        Device Device = entityManager.find(Device.class, l);
        entityManager.remove(Device);
    }
}
