package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.ValveCardTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface ValveCardTestRepository {
    ValveCardTestData save(ValveCardTestData valveCardTestData);

    List<ValveCardTestData> findByCustomQuery(TypedQuery<ValveCardTestData> query, int pageNo);

    List<ValveCardTestData> findByCustomQuery(TypedQuery<ValveCardTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    ValveCardTestData findVerifiedByCode(String code);

    ValveCardTestData findByCode(String code);
}
