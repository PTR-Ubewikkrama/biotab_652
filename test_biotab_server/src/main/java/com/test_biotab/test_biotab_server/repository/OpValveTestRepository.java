package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.OpValveTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface OpValveTestRepository {

    OpValveTestData save(OpValveTestData opValveTestData);

    List<OpValveTestData> findByCustomQuery(TypedQuery<OpValveTestData> query, int pageNo);

    List<OpValveTestData> findByCustomQuery(TypedQuery<OpValveTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    OpValveTestData findByCode(String code);
}
