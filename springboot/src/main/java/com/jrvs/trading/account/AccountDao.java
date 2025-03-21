package com.jrvs.trading.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {
    Account save(Account account);
    //List<Account> saveAll(List<Account> accounts);
    List<Account> findAll();
    List<Account> findAllById(Iterable<Integer> ids);
    Account getAccountByTraderId(Integer traderId);
    Optional<Account> findById(Integer id);
    boolean existsById(Integer id);
    void deleteById(Integer id);
    long count();
    void deleteAll();
}
