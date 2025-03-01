package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.FinalAssembly;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface FinalAssemblyRepository {
    FinalAssembly findByDeviceId(String code);
    
    FinalAssembly save(FinalAssembly finalAssembly);

    List<FinalAssembly> findByCustomQuery(TypedQuery<FinalAssembly> query, int pageNo);

    List<FinalAssembly> findByCustomQuery(TypedQuery<FinalAssembly> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);

    FinalAssembly findByCustomQueryForSingle(TypedQuery<FinalAssembly> query);
}
