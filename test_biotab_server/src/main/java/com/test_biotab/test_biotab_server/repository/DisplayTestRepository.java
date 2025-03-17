package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.DisplayTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface DisplayTestRepository {
    DisplayTestData save(DisplayTestData displayTestData);

    List<DisplayTestData> findByCustomQuery(TypedQuery<DisplayTestData> query, int pageNo);

    List<DisplayTestData> findByCustomQuery(TypedQuery<DisplayTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    DisplayTestData findByCode(String code);
}
