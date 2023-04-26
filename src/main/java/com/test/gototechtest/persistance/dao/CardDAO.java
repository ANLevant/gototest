package com.test.gototechtest.persistance.dao;

import com.test.gototechtest.persistance.entities.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDAO extends CrudRepository<Card, Long> {
}
