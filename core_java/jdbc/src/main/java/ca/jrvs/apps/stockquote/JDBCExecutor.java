package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JDBCExecutor {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/stock_quote", "username", "password");
            QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();
            QuoteDao quoteDao = new QuoteDao(connection);
            PositionDao positionDao = new PositionDao(connection);
            QuoteService service = new QuoteService(quoteDao, quoteHttpHelper);
            PositionService positionService = new PositionService(positionDao, service);

            try {
                Quote fail = service.fetchQuoteDataFromAPI("SADAS").get();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid symbol");
            }

            Quote quote = service.fetchQuoteDataFromAPI("MSFT").get();
            System.out.println(quote.getSymbol());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
