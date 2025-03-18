package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.PowerSupplyTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface PowerSupplyTestRepository {
    PowerSupplyTestData save(PowerSupplyTestData airPumpTestData);

    List<PowerSupplyTestData> findByCustomQuery(TypedQuery<PowerSupplyTestData> query, int pageNo);

    List<PowerSupplyTestData> findByCustomQuery(TypedQuery<PowerSupplyTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    PowerSupplyTestData findVerifiedByCode(String code);

    PowerSupplyTestData findByCode(String code);
}
