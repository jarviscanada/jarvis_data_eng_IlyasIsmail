package com.jrvs.trading.marketData;

import com.jrvs.trading.TestConfig;
import com.jrvs.trading.quote.Quote;
import com.jrvs.trading.quote.QuoteDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class QuoteDao_IntTest {

    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;


    @BeforeEach
    public void insertOne() throws ParseException {
        String date = "2024-02-12";
        Date fixedDate = new SimpleDateFormat("2024-02-12").parse(date);
        savedQuote.setTicker("Ticker");
        savedQuote.setChange(4);
        savedQuote.setHigh(5);
        savedQuote.setLow(4);
        savedQuote.setChangePercent("50");
        savedQuote.setOpen(4);
        savedQuote.setLatestTradingDay(fixedDate);
        savedQuote.setPrice(500);
        savedQuote.setVolume(5000);
        savedQuote.setPreviousClose(59);
        quoteDao.save(savedQuote);
    }

    @Test
    public void findAll() {
        List<Quote> quotes = quoteDao.findAll();

        Assertions.assertEquals(savedQuote.getPrice(), quotes.get(0).getPrice());
    }

    @Test
    public void findAllById() {
        List<String> tickers = new ArrayList<>();
        tickers.add("Ticker");
        List<Quote> quotes = quoteDao.findAllById(tickers);

        Assertions.assertEquals(savedQuote.getPrice(), quotes.get(0).getPrice());
    }

    @Test
    public void findById() {
        Quote quote = quoteDao.findById("Ticker").get();

        Assertions.assertEquals(savedQuote.getTicker(), quote.getTicker());
    }

    @Test
    public void existsById() {
        Assertions.assertTrue(quoteDao.existsById("Ticker"));
    }

    @Test
    public void deleteById() {
        quoteDao.deleteById("Ticker");
        Assertions.assertFalse(quoteDao.existsById("Ticker"));
    }

    @Test
    public void count() {
        Assertions.assertEquals(1, quoteDao.count());
    }

    @Test
    public void deleteAllById() {
        List<String> tickers = new ArrayList<>();
        tickers.add("Ticker");
        quoteDao.deleteAllById(tickers);

        Assertions.assertFalse(quoteDao.existsById("Ticker"));
    }

    @Test
    public void test() {
        System.out.println("test");
    }

    @AfterEach
    public void deleteOne() {
        quoteDao.deleteById(savedQuote.getTicker());
    }
}
