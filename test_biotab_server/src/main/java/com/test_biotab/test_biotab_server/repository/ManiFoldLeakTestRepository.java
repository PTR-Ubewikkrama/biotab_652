package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.ManiFoldLeakTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface ManiFoldLeakTestRepository {
    ManiFoldLeakTestData save(ManiFoldLeakTestData maniFoldLeakTestData);

    List<ManiFoldLeakTestData> findByCustomQuery(TypedQuery<ManiFoldLeakTestData> query, int pageNo);

    List<ManiFoldLeakTestData> findByCustomQuery(TypedQuery<ManiFoldLeakTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    ManiFoldLeakTestData findVerifiedByCode(String code);

    ManiFoldLeakTestData findByCode(String code);
}
