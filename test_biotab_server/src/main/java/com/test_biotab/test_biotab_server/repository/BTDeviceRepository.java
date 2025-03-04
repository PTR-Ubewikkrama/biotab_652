package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.BTDevice;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface BTDeviceRepository {

    BTDevice findByCode(String code);

    BTDevice save(BTDevice btDevice);

    List<BTDevice> findByCustomQuery(TypedQuery<BTDevice> query, int pageNo);

    List<BTDevice> findByCustomQuery(TypedQuery<BTDevice> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteByCode(String code);
}
