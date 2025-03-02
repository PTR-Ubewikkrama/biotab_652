package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.FanTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface FanTestRepository {

    FanTestData save(FanTestData fanTestData);

    List<FanTestData> findByCustomQuery(TypedQuery<FanTestData> query, int page);

    List<FanTestData> findByCustomQuery(TypedQuery<FanTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    FanTestData findByCode(String code);
}
