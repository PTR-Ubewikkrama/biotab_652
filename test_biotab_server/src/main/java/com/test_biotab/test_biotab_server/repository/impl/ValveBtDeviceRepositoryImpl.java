package com.test_biotab.test_biotab_server.repository.impl;

import com.test_biotab.test_biotab_server.entity.ValveCardBTDevice;
import com.test_biotab.test_biotab_server.repository.FanTestRepository;
import com.test_biotab.test_biotab_server.repository.ValveBtDeviceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@Slf4j
public class ValveBtDeviceRepositoryImpl implements ValveBtDeviceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ValveCardBTDevice save(ValveCardBTDevice valveCardBTDevice) {
        log.info("Saving ValveCardBTDevice: {}", valveCardBTDevice.getBtDeviceCode());
        return entityManager.merge(valveCardBTDevice);
    }

    @Override
    public List<ValveCardBTDevice> findByCustomQuery(TypedQuery<ValveCardBTDevice> query, int page) {
        log.info("Finding ValveCardBTDevice by custom query: {}", query.toString());
        return query
                .setFirstResult(page * 15)
                .setMaxResults(15)
                .getResultList();
    }

    @Override
    public List<ValveCardBTDevice> findByCustomQuery(TypedQuery<ValveCardBTDevice> query) {
        log.info("Finding ValveCardBTDevice by custom query: {}", query.toString());
        return query.getResultList();
    }

    @Override
    public Long countByCustomQuery(TypedQuery<Long> query) {
        log.info("Counting ValveCardBTDevice by custom query: {}", query.toString());
        return query.getSingleResult();
    }

    @Override
    public ValveCardBTDevice findByCode(String code) {
        log.info("Finding ValveCardBTDevice by code: {}", code);
        TypedQuery<ValveCardBTDevice> query = entityManager.createQuery("SELECT v FROM ValveCardBTDevice v WHERE v.valveCode = :code AND v.valveCodeStatus = :status", ValveCardBTDevice.class);
        query.setParameter("code", code);
        query.setParameter("status", true);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            log.error("Error while finding ValveCardBTDevice by code: {}", code, e);
            return null;
        }
    }

    @Override
    public List<ValveCardBTDevice> findByBtDeviceCode(Integer deviceId) {
        log.info("Finding ValveCardBTDevice by BT device code: {}", deviceId);
        TypedQuery<ValveCardBTDevice> query = entityManager.createQuery("SELECT v FROM ValveCardBTDevice v WHERE v.btDeviceCode = :deviceId", ValveCardBTDevice.class);
        query.setParameter("deviceId", deviceId);

        try {
            return query.getResultList();
        } catch (Exception e) {
            log.error("Error while finding ValveCardBTDevice by BT device code: {}", deviceId, e);
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteByBtDeviceCode(Integer deviceId) {
        log.info("Deleting ValveCardBTDevice by BT device code: {}", deviceId);
        TypedQuery<ValveCardBTDevice> query = entityManager.createQuery("SELECT v FROM ValveCardBTDevice v WHERE v.btDeviceCode = :deviceId", ValveCardBTDevice.class);
        query.setParameter("deviceId", deviceId);

        try {
            List<ValveCardBTDevice> valveCardBTDevices = query.getResultList();
            for (ValveCardBTDevice valveCardBTDevice : valveCardBTDevices) {
                entityManager.remove(valveCardBTDevice);
            }
        } catch (Exception e) {
            log.error("Error while deleting ValveCardBTDevice by BT device code: {}", deviceId, e);
        }
    }
}
