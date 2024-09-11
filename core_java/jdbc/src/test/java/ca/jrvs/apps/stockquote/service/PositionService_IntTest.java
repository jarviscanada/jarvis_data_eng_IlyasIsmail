package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PositionService_IntTest {

    private PositionService positionService;
    private PositionDao positionDao;
    private Position position;
    private QuoteService quoteService;
    private QuoteDao quoteDao;
    private QuoteHttpHelper quoteHttpHelper;
    private static Connection c;
    private static Map<String, String> properties = new HashMap<>();

    @BeforeClass
    public static void setUpDB() throws SQLException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/properties.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");
                properties.put(tokens[0], tokens[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName(properties.get("db-class"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:postgresql://"+properties.get("server")+"/"+properties.get("database");
        c = DriverManager.getConnection(url, properties.get("username"), properties.get("password"));
    }

    @Before
    public void setUp() throws Exception {
        positionDao = new PositionDao(c);
        quoteDao = new QuoteDao(c);
        quoteHttpHelper = new QuoteHttpHelper(properties.get("api-key"));
        quoteService = new QuoteService(quoteDao, quoteHttpHelper);
        positionService = new PositionService(positionDao, quoteService);
    }

    @Test
    public void buy() {
        position = positionDao.findById("MSFT").get();
        positionDao.deleteById("MSFT");
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
        positionDao.deleteById("MSFT");
        positionService.buy("MSFT", 1000000000, 1);
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