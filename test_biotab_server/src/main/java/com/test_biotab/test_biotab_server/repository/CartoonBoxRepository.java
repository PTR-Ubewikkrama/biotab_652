package com.test_biotab.test_biotab_server.repository;

import com.test_biotab.test_biotab_server.entity.CartoonBox;
import jakarta.persistence.TypedQuery;

import java.util.List;

public interface CartoonBoxRepository {
    CartoonBox findByCartoonNumber(String cartoonNumber);
    
    CartoonBox save(CartoonBox cartoonBox);

    List<CartoonBox> findByCustomQuery(TypedQuery<CartoonBox> query, int pageNo);

    List<CartoonBox> findByCustomQuery(TypedQuery<CartoonBox> query);

    Long countByCustomQuery(TypedQuery<Long> query);

    void deleteById(int l);

    CartoonBox findByCustomQueryForSingle(TypedQuery<CartoonBox> query);
}
