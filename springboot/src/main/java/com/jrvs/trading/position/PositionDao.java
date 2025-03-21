package com.jrvs.trading.position;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionDao extends JpaRepository<Position, Integer> {
    List<Position> findAll();
    List<Position> findAllById(Iterable<Integer> ids);
    List<Position> findByAccountId(Integer accountId);
    Optional<Position> findById(Integer id);
    boolean existsById(Integer id);
    long count();
}
