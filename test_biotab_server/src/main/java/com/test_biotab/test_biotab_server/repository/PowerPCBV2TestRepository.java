package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.PowerPCBV2TestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface PowerPCBV2TestRepository {
    PowerPCBV2TestData save(PowerPCBV2TestData powerPCBV2TestData);

    List<PowerPCBV2TestData> findByCustomQuery(TypedQuery<PowerPCBV2TestData> query, int pageNo);

    List<PowerPCBV2TestData> findByCustomQuery(TypedQuery<PowerPCBV2TestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    PowerPCBV2TestData findVerifiedByCode(String code);

    PowerPCBV2TestData findByCode(String code);
}
