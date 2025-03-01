package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.BatteryTestData;
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
