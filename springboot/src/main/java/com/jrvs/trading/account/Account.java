package com.jrvs.trading.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    int traderId;
    double amount;

    public Account() {
    }

    public Account(int id, int traderId, double amount) {
        this.id = id;
        this.traderId = traderId;
        this.amount = amount;
    }

    public Account(int traderId, double amount) {
        this.traderId = traderId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTraderId() {
        return traderId;
    }

    public void setTraderId(int traderId) {
        this.traderId = traderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
