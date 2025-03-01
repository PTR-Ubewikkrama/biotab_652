package com.test_jig.test_jig_server.repository;

import com.test_jig.test_jig_server.entity.PcbTestData;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface PcbTestRepository {
    PcbTestData save(PcbTestData pcbTestData);

    List<PcbTestData> findByCustomQuery(TypedQuery<PcbTestData> query, int pageNo);

    List<PcbTestData> findByCustomQuery(TypedQuery<PcbTestData> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);

    PcbTestData findByCode(String code);
}
