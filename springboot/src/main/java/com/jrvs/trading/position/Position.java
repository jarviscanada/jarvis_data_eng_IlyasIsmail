package com.jrvs.trading.position;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Position {

    @Id
    int id;
    int accountId;
    String ticker;
    int total;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
