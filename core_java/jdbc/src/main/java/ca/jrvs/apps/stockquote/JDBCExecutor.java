package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.controller.StockQuoteController;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import okhttp3.OkHttpClient;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class JDBCExecutor {

    private final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);

    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("Please input the path of the properties.txt file.");
        }

        JDBCExecutor jdbcExecutor = new JDBCExecutor();

        BasicConfigurator.configure();

        Map<String, String> properties = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");
                properties.put(tokens[0], tokens[1]);
            }
        } catch (FileNotFoundException e) {
            jdbcExecutor.logger.error("The properties.txt file was not found inside src/main/resources.", e);
        } catch (IOException e) {
            jdbcExecutor.logger.error("There was an error with the input/output process for the properties.txt file.", e);
        }

        try {
            Class.forName(properties.get("db-class"));
        } catch (ClassNotFoundException e) {
            jdbcExecutor.logger.error("The provided driver class for the database could not be found.", e);
        }

        String url = "jdbc:postgresql://"+properties.get("server")+"/"+properties.get("database");

        try (Connection connection = DriverManager.getConnection(url, properties.get("username"), properties.get("password"));) {
            QuoteDao quoteDao = new QuoteDao(connection);
            PositionDao positionDao = new PositionDao(connection);
            QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper(properties.get("api-key"));
            QuoteService quoteService = new QuoteService(quoteDao, quoteHttpHelper);
            PositionService positionService = new PositionService(positionDao, quoteService);
            StockQuoteController stockQuoteController = new StockQuoteController(quoteService, positionService, positionDao);
            stockQuoteController.initClient();
        } catch (SQLException e) {
            jdbcExecutor.logger.error("There was an error connecting to the database. Please ensure the database is running and the proper credentials are provided.", e);
            throw new RuntimeException("SQL connection error: ", e);
        }
    }
}
