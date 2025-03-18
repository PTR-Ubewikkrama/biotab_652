package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.ValveSequenceTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface ValveSequenceTestRepository {
    ValveSequenceTestData save(ValveSequenceTestData valveSequenceTestData);

    List<ValveSequenceTestData> findByCustomQuery(TypedQuery<ValveSequenceTestData> query, int pageNo);

    List<ValveSequenceTestData> findByCustomQuery(TypedQuery<ValveSequenceTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    ValveSequenceTestData findVerifiedByCode(String code);

    ValveSequenceTestData findByCode(String code);
}
