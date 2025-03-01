package com.test_jig.test_jig_server.repository;

import com.test_jig.test_jig_server.entity.HHDevice;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface HHDeviceRepository {

    HHDevice findByCode(String code);

    HHDevice save(HHDevice hhDevice);

    List<HHDevice> findByCustomQuery(TypedQuery<HHDevice> query, int pageNo);

    List<HHDevice> findByCustomQuery(TypedQuery<HHDevice> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteByCode(String code);
}
