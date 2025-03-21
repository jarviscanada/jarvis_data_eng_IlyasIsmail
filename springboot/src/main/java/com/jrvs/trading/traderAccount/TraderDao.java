package com.jrvs.trading.traderAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TraderDao extends JpaRepository<Trader, Integer> {
    Trader save(Trader trader);
    //List<Trader> saveAll(List<Trader> traders);
    List<Trader> findAll();
    List<Trader> findAllById(Iterable<Integer> ids);
    Optional<Trader> findById(Integer id);
    boolean existsById(Integer id);
    void deleteById(Integer id);
    long count();
    void deleteAll();
}
