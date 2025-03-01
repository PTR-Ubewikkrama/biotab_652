package com.test_jig.test_jig_server.repository;

import com.test_jig.test_jig_server.entity.AirPumpTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface AirPumpTestRepository {
    AirPumpTestData save(AirPumpTestData airPumpTestData);

    List<AirPumpTestData> findByCustomQuery(TypedQuery<AirPumpTestData> query, int pageNo);

    List<AirPumpTestData> findByCustomQuery(TypedQuery<AirPumpTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);

    AirPumpTestData findByCode(String code);
}
