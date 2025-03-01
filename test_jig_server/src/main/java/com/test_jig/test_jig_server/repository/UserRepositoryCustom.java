package com.test_jig.test_jig_server.repository;

import com.test_jig.test_jig_server.entity.User;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface UserRepositoryCustom {
    User findByEmail(String id);

    User save(User User);

    List<User> findByCustomQuery(TypedQuery<User> query, int pageNo);

    List<User> findByCustomQuery(TypedQuery<User> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);
}
