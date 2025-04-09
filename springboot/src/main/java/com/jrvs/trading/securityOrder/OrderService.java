package com.jrvs.trading.securityOrder;

import com.jrvs.trading.account.Account;
import com.jrvs.trading.account.AccountDao;
import com.jrvs.trading.position.Position;
import com.jrvs.trading.position.PositionDao;
import com.jrvs.trading.quote.Quote;
import com.jrvs.trading.quote.QuoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

    AccountDao accountDao;
    SecurityOrderDao securityOrderDao;
    QuoteDao quoteDao;
    PositionDao positionDao;

    @Autowired
    public OrderService (AccountDao accountDao, SecurityOrderDao securityOrderDao, QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    public SecurityOrder executeMarketOrder(MarketOrder orderData) {
        Quote quote = quoteDao.findById(orderData.getTicker()).get();

        if (quote.getTicker() == null) {
            throw new IllegalArgumentException("There is no stock with this ticker.");
        }

        if (orderData.getSize() > quote.getVolume()) {
            throw new IllegalArgumentException("The amount you entered is too high, max amount available is " + quote.getVolume() + ".");
        }

        Account account = accountDao.getAccountByTraderId(orderData.getTraderId());

        if (account == null) {
            throw new IllegalArgumentException("The given trader id has no associated account.");
        }

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.accountId = account.getId();
        securityOrder.ticker = orderData.getTicker();
        securityOrder.status = "Pending";
        securityOrder.size = orderData.getSize();
        securityOrder.price = orderData.getSize() * quote.getPrice();

        switch (orderData.getChoice()) {
            case BUY:
                if(account.getAmount() < securityOrder.price) {
                    throw new IllegalArgumentException("Insufficient funds.");
                }

                account.setAmount(account.getAmount() - securityOrder.price);
                accountDao.save(account);
                securityOrder.status = "Filled";
            case SELL:
                List<Position> positions = positionDao.findByAccountId(account.getId());
                int ownedPositions = 0;
                boolean owned = false;

                 for (Position position : positions) {
                     if (Objects.equals(position.getTicker(), orderData.getTicker())) {
                         owned = true;
                         ownedPositions = position.getTotal();
                         break;
                     }
                 }

                 if(!owned) {
                     throw new IllegalArgumentException("You do not own any position by the given ticker.");
                 }

                 if(ownedPositions > securityOrder.size) {
                     throw new IllegalArgumentException("You do not own enough positions with the given ticker.");
                 }

                 account.setAmount(account.getAmount() + securityOrder.price);
                 accountDao.save(account);
                 securityOrder.status = "Filled";
        }
        return securityOrderDao.save(securityOrder);
    }
}
