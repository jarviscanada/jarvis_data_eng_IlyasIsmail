package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;

import java.util.Optional;

public class QuoteService {

    private QuoteDao dao;
    private QuoteHttpHelper httpHelper;

    public QuoteService(QuoteDao dao, QuoteHttpHelper httpHelper) {
        this.dao = dao;
        this.httpHelper = httpHelper;
    }

    public Optional<Quote> fetchQuoteDataFromAPI(String symbol) {
        return Optional.of(httpHelper.fetchQuoteInfo(symbol));
    }
}
