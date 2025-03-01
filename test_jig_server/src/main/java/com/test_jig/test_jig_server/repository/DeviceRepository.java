package com.test_jig.test_jig_server.repository;

import com.test_jig.test_jig_server.entity.Device;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface DeviceRepository {
    Device findById(int id);

    Device findByMac(String mac);

    Device save(Device device);

    List<Device> findByCustomQuery(TypedQuery<Device> query, int pageNo);

    List<Device> findByCustomQuery(TypedQuery<Device> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);
}
