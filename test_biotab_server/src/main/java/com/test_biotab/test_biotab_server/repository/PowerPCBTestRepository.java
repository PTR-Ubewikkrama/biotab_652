package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.PowerPCBTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface PowerPCBTestRepository {
    PowerPCBTestData save(PowerPCBTestData powerPCBTestData);

    List<PowerPCBTestData> findByCustomQuery(TypedQuery<PowerPCBTestData> query, int pageNo);

    List<PowerPCBTestData> findByCustomQuery(TypedQuery<PowerPCBTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    PowerPCBTestData findByCode(String code);
}
