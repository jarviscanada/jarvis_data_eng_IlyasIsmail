package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class QuoteService_IntTest {

    QuoteService quoteService;
    QuoteDao quoteDao;
    QuoteHttpHelper quoteHttpHelper;
    private static Connection c;

    @BeforeClass
    public static void setUpDB() throws SQLException {
        c = DriverManager.getConnection("jdbc:postgresql://localhost/stock_quote", "username", "password");
    }

    @Before
    public void setUp() throws Exception {
        quoteDao = new QuoteDao(c);
        quoteHttpHelper = new QuoteHttpHelper();
        quoteService = new QuoteService(quoteDao, quoteHttpHelper);
    }

    @Test
    public void fetchQuoteDataFromAPI() {
        assertNotNull(quoteService.fetchQuoteDataFromAPI("MSFT").get().getLatestTradingDay());
    }
}