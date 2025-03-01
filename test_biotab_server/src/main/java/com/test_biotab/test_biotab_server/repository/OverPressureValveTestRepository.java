package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.OverPressureValveTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface OverPressureValveTestRepository {
    OverPressureValveTestData save(OverPressureValveTestData overPressureValveTestData);

    List<OverPressureValveTestData> findByCustomQuery(TypedQuery<OverPressureValveTestData> query, int pageNo);

    List<OverPressureValveTestData> findByCustomQuery(TypedQuery<OverPressureValveTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);

    OverPressureValveTestData findByCode(String code);
}
