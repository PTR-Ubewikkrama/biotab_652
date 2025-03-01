package com.test_jig.test_jig_server.repository;

import com.test_jig.test_jig_server.entity.PowerSupplyTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface PowerSupplyTestRepository {
    PowerSupplyTestData save(PowerSupplyTestData airPumpTestData);

    List<PowerSupplyTestData> findByCustomQuery(TypedQuery<PowerSupplyTestData> query, int pageNo);

    List<PowerSupplyTestData> findByCustomQuery(TypedQuery<PowerSupplyTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);

    PowerSupplyTestData findByCode(String code);
}
