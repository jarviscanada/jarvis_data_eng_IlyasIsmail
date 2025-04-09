package com.jrvs.trading.securityOrder;

public class MarketOrder {

    private String ticker;
    private int size;
    private int traderId;
    enum Option { BUY, SELL }
    private Option choice;

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

    public int getTraderId() {
        return traderId;
    }

    public void setTraderId(int traderId) {
        this.traderId = traderId;
    }

    public Option getChoice() {
        return choice;
    }

    public void setChoice(Option choice) {
        this.choice = choice;
    }
}