package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.UiPcbTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface UiPcbTestRepository {

    UiPcbTestData save(UiPcbTestData uiPcbTestData);

    List<UiPcbTestData> findByCustomQuery(TypedQuery<UiPcbTestData> query, int pageNo);

    List<UiPcbTestData> findByCustomQuery(TypedQuery<UiPcbTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    UiPcbTestData findVerifiedByCode(String code);

    UiPcbTestData findByCode(String code);
}
