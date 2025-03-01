package com.test_jig.test_jig_server.repository;

import com.test_jig.test_jig_server.entity.ValveTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface ValveTestRepository {
    ValveTestData save(ValveTestData valveTestData);

    List<ValveTestData> findByCustomQuery(TypedQuery<ValveTestData> query, int pageNo);

    List<ValveTestData> findByCustomQuery(TypedQuery<ValveTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);

    ValveTestData findByCode(String code);
}
