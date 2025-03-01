package com.test_jig.test_jig_server.repository;

import com.test_jig.test_jig_server.entity.BatteryTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface BatteryTestRepository {
    BatteryTestData save(BatteryTestData batteryTestData);

    List<BatteryTestData> findByCustomQuery(TypedQuery<BatteryTestData> query, int pageNo);

    List<BatteryTestData> findByCustomQuery(TypedQuery<BatteryTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);

    BatteryTestData findByCode(String code);
}
