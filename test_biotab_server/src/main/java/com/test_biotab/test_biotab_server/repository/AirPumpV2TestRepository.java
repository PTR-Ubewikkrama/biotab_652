package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.AirPumpV2TestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface AirPumpV2TestRepository {
    AirPumpV2TestData save(AirPumpV2TestData airPumpV2TestData);

    List<AirPumpV2TestData> findByCustomQuery(TypedQuery<AirPumpV2TestData> query, int pageNo);

    List<AirPumpV2TestData> findByCustomQuery(TypedQuery<AirPumpV2TestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    AirPumpV2TestData findByCode(String code);
}
