package com.jrvs.trading.securityOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityOrderDao extends JpaRepository<SecurityOrder, Integer> {
    SecurityOrder save(SecurityOrder securityOrder);
    //List<SecurityOrder> saveAll(List<SecurityOrder> securityOrders);
    List<SecurityOrder> findAll();
    List<SecurityOrder> findAllById(Iterable<Integer> ids);
    Optional<SecurityOrder> findById(Integer id);
    boolean existsById(Integer id);
    void deleteById(Integer id);
    void deleteAllByAccountId(Integer accountId);
    void deleteAllByAccountIdAndTicker(Integer accountId, String ticker);
    long count();
    void deleteAll();
}
