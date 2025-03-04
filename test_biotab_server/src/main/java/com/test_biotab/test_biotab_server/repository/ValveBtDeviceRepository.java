package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.ValveCardBTDevice;
import jakarta.persistence.TypedQuery;

import java.util.Collection;
import java.util.List;

public interface ValveBtDeviceRepository {

    ValveCardBTDevice save(ValveCardBTDevice valveCardBTDevice);

    List<ValveCardBTDevice> findByCustomQuery(TypedQuery<ValveCardBTDevice> query, int page);

    List<ValveCardBTDevice> findByCustomQuery(TypedQuery<ValveCardBTDevice> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    ValveCardBTDevice findByCode(String code);

    List<ValveCardBTDevice> findByBtDeviceCode(Integer deviceId);

    void deleteByBtDeviceCode(Integer deviceId);
}
