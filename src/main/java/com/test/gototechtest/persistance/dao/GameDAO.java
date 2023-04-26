package com.test.gototechtest.persistance.dao;

import com.test.gototechtest.persistance.entities.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDAO extends CrudRepository<Game, Long> {
}
