package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.AirPumpTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface AirPumpTestRepository {
    AirPumpTestData save(AirPumpTestData airPumpTestData);

    List<AirPumpTestData> findByCustomQuery(TypedQuery<AirPumpTestData> query, int pageNo);

    List<AirPumpTestData> findByCustomQuery(TypedQuery<AirPumpTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    AirPumpTestData findVerifiedByCode(String code);

    AirPumpTestData findByCode(String code);
}
