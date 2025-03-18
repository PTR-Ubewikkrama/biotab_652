package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.MainPCBTestUnit;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface MainPCBTestUnitRepository {
    MainPCBTestUnit save(MainPCBTestUnit mainPCBTestUnit);

    List<MainPCBTestUnit> findByCustomQuery(TypedQuery<MainPCBTestUnit> query, int pageNo);

    List<MainPCBTestUnit> findByCustomQuery(TypedQuery<MainPCBTestUnit> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    List<MainPCBTestUnit> getMainPCBTestUnitByMainTestId(int mainTestId);
}
