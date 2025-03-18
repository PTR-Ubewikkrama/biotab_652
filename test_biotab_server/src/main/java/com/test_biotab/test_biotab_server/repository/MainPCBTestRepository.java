package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.MainPCBTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface MainPCBTestRepository {
    MainPCBTestData save(MainPCBTestData mainPCBTestData);

    List<MainPCBTestData> findByCustomQuery(TypedQuery<MainPCBTestData> query, int pageNo);

    List<MainPCBTestData> findByCustomQuery(TypedQuery<MainPCBTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    MainPCBTestData findVerifiedByCode(String code);

    MainPCBTestData findByCode(String code);
}
