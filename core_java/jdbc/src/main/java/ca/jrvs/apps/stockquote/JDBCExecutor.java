package ca.jrvs.apps.stockquote;

import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JDBCExecutor {
    public static void main(String[] args) {
        QuoteHttpHelper quoteHttpHelper = new QuoteHttpHelper();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/stock_quote", "username", "password");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
