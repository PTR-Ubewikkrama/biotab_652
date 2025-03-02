package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.CableTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface CableTestRepository {

    CableTestData save(CableTestData cableTestData);

    List<CableTestData> findByCustomQuery(TypedQuery<CableTestData> query, int page);

    List<CableTestData> findByCustomQuery(TypedQuery<CableTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    CableTestData findByCode(String code);
}
