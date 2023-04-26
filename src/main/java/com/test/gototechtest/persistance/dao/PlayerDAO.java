package com.test.gototechtest.persistance.dao;

import com.test.gototechtest.persistance.entities.Player;
import com.test.gototechtest.persistance.entities.Shoe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerDAO extends CrudRepository<Player, Long> {
}
