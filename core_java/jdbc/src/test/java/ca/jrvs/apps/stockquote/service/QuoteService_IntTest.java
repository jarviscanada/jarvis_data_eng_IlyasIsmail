package ca.jrvs.apps.stockquote.service;

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

public class QuoteService_IntTest {

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
        quoteDao = new QuoteDao(c);
        quoteHttpHelper = new QuoteHttpHelper(properties.get("api-key"));
        quoteService = new QuoteService(quoteDao, quoteHttpHelper);
    }

    @Test
    public void fetchQuoteDataFromAPI() {
        assertNotNull(quoteService.fetchQuoteDataFromAPI("MSFT").get().getLatestTradingDay());
    }
}