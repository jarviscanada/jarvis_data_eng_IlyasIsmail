package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class PositionService_IntTest {

    PositionService positionService;
    PositionDao positionDao;
    Position position;
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
        positionDao = new PositionDao(c);
        quoteDao = new QuoteDao(c);
        quoteHttpHelper = new QuoteHttpHelper();
        quoteService = new QuoteService(quoteDao, quoteHttpHelper);
        positionService = new PositionService(positionDao, quoteService);
    }

    @Test
    public void buy() {
        position = positionDao.findById("MSFT").get();
        positionService.buy("MSFT", 10, 10);
        assertEquals(positionDao.findById("MSFT").get().getNumOfShares(), 10);
        positionDao.deleteById("MSFT");

        if(position.getSymbol() != null) {
            positionDao.save(position);
        }
    }

    @Test
    public void buyOverVolume() {
        position = positionDao.findById("MSFT").get();
        positionService.buy("MSFT", 1000000000, 100);
        assertEquals(positionDao.findById("MSFT").get().getNumOfShares(), quoteHttpHelper.fetchQuoteInfo("MSFT").getVolume());
        positionDao.deleteById("MSFT");

        if(position.getSymbol() != null) {
            positionDao.save(position);
        }
    }

    @Test
    public void sell() {
        position = positionDao.findById("MSFT").get();
        positionService.buy("MSFT", 10, 10);
        positionService.sell("MSFT");
        assertNull(positionDao.findById("MSFT").get().getSymbol());

        if(position.getSymbol() != null) {
            positionDao.save(position);
        }

    }
}