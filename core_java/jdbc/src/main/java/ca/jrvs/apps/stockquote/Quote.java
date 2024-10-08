package ca.jrvs.apps.stockquote;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.sql.Timestamp;

public class Quote {

    private String symbol; //id
    private double open;
    private double high;
    private double low;
    private double price;
    private int volume;
    private Date latestTradingDay;
    private double previousClose;
    private double change;
    private String changePercent;
    private Timestamp timestamp;

    public Quote() {

    }

    public Quote(String symbol, double open, double high, double low, double price, int volume, Date latestTradingDay, double previousClose, double change, String changePercent, Timestamp timestamp) {
        this.symbol = symbol;
        this.open = open;
        this.high = high;
        this.low = low;
        this.price = price;
        this.volume = volume;
        this.latestTradingDay = latestTradingDay;
        this.previousClose = previousClose;
        this.change = change;
        this.changePercent = changePercent;
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Date getLatestTradingDay() {
        return latestTradingDay;
    }

    public void setLatestTradingDay(Date latestTradingDay) {
        this.latestTradingDay = latestTradingDay;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "--------------------------------------------" + System.lineSeparator() +
                "Quote: " + System.lineSeparator() +
                "Symbol: " + symbol + System.lineSeparator() +
                "Open: " + open + System.lineSeparator() +
                "High: " + high + System.lineSeparator() +
                "Low: " + low + System.lineSeparator() +
                "Price: " + price + System.lineSeparator() +
                "Volume: " + volume + System.lineSeparator() +
                "Latest Trading Day: "  + latestTradingDay + System.lineSeparator() +
                "Previous Close: " + previousClose + System.lineSeparator() +
                "Change: " + change + System.lineSeparator() +
                "Change Percent: " + changePercent + System.lineSeparator();
    }
}
