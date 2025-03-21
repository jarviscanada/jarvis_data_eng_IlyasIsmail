package com.jrvs.trading.marketData;

import com.jrvs.trading.TestConfig;
import com.jrvs.trading.quote.Quote;
import com.jrvs.trading.quote.QuoteDao;
import com.jrvs.trading.quote.QuoteService;
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

@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteService_IntTest {

    @Autowired
    private QuoteService quoteService;
    
    @Autowired
    private QuoteDao quoteDao;

    @BeforeEach
    public void setup() {
        quoteDao.deleteAll();
    }

    @Test
    public void findIexQuoteByTicker() {
        Quote quote = quoteService.findQuoteByTicker("IBM");
        System.out.println(quote.getTicker());
        Assertions.assertEquals("IBM", quote.getTicker());
    }

    @Test
    public void updateMarketData() {
        Quote quote = quoteDao.findById("IBM").get();
        Quote vantageQuote = quoteService.findQuoteByTicker("IBM");
        Assertions.assertEquals(vantageQuote.getVolume(), quote.getVolume());
    }

    @Test
    public void saveQuotes() {
        List<String> tickers = new ArrayList<>();
        tickers.add("IBM");
        tickers.add("MSFT");
        List<Quote> quotes = quoteService.saveQuotes(tickers);
        for(Quote quote : quotes) {
            Quote dbQuote = quoteDao.findById(quote.getTicker()).get();
            Assertions.assertEquals(quote.getOpen(), dbQuote.getOpen());
        }
    }

    @Test
    public void saveQuote() throws ParseException {
        Quote savedQuote = new Quote();
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
        quoteService.saveQuote(savedQuote);

        Quote quote = quoteDao.findById(savedQuote.getTicker()).get();

        Assertions.assertEquals(savedQuote.getHigh(), quote.getHigh());
    }

    @Test
    public void findAllQuotes() {
        List<Quote> quotes = quoteService.findAllQuotes();

        Assertions.assertFalse(quotes.isEmpty());
    }
}
