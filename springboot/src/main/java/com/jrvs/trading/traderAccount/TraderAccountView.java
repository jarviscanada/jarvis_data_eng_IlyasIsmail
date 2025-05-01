package com.jrvs.trading.traderAccount;

import java.util.Date;

public class TraderAccountView {
    int traderId;
    int accountId;
    String firstName;
    String lastName;
    double amount;
    Date dob;
    String country;
    String email;

    public TraderAccountView(int traderId, int accountId, String firstName, String lastName, double amount, Date dob, String country, String email) {
        this.traderId = traderId;
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amount = amount;
        this.dob = dob;
        this.country = country;
        this.email = email;
    }

    public int getTraderId() {
        return traderId;
    }

    public void setTraderId(int traderId) {
        this.traderId = traderId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
