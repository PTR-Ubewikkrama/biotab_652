package com.test_jig.test_jig_server.repository;

import com.test_jig.test_jig_server.entity.LatchButtonTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface LatchButtonTestRepository {
    LatchButtonTestData save(LatchButtonTestData latchButtonTestData);

    List<LatchButtonTestData> findByCustomQuery(TypedQuery<LatchButtonTestData> query, int pageNo);

    List<LatchButtonTestData> findByCustomQuery(TypedQuery<LatchButtonTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);

    LatchButtonTestData findByCode(String code);
}
