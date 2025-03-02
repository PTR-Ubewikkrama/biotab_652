package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.PowerSupplyV2TestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface PowerSupplyV2TestRepository {

    PowerSupplyV2TestData save(PowerSupplyV2TestData airPumpTestData);

    List<PowerSupplyV2TestData> findByCustomQuery(TypedQuery<PowerSupplyV2TestData> query, int pageNo);

    List<PowerSupplyV2TestData> findByCustomQuery(TypedQuery<PowerSupplyV2TestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    PowerSupplyV2TestData findByCode(String code);
}
