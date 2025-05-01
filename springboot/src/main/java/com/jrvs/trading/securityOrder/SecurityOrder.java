package com.jrvs.trading.securityOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SecurityOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    int accountId;
    String status;
    String ticker;
    int size;
    double price;
    String notes;

    public SecurityOrder() {
    }

    public SecurityOrder(int accountId, String status, String ticker, int size, double price, String notes) {
        this.accountId = accountId;
        this.status = status;
        this.ticker = ticker;
        this.size = size;
        this.price = price;
        this.notes = notes;
    }

    public SecurityOrder(int id, int accountId, String status, String ticker, int size, double price, String notes) {
        this.id = id;
        this.accountId = accountId;
        this.status = status;
        this.ticker = ticker;
        this.size = size;
        this.price = price;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
