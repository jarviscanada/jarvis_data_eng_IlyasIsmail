package com.jrvs.trading.marketData;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuoteService {

    /**
     * Find an IexQuote
     * @param ticker
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */

    private MarketDataDao dao;

    public QuoteService(MarketDataDao dao) {
        this.dao = dao;
    }

    public Quote findQuoteByTicker(String ticker) {

        Optional<Quote> quote;

        if ((quote = dao.findById(ticker)).isPresent()) {
            return quote.get();
        } else {
            throw new IllegalArgumentException("Invalid symbol or too many requests.");
        }
    }

}