package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.Quote;
import com.sun.corba.se.impl.ior.GenericTaggedComponent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class QuoteDao implements CrudDao<Quote, String>{

    private Connection c;

    private static final String UPSERT = "INSERT INTO quote (symbol, open, high, low, price, volume, latest_trading_day, " +
            "previous_close, change, change_percent, timestamp) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
            "ON CONFLICT(symbol) " +
            "DO UPDATE SET " +
            "symbol = EXCLUDED.symbol, " +
            "open = EXCLUDED.open, " +
            "high = EXCLUDED.high, " +
            "low = EXCLUDED.low, " +
            "price = EXCLUDED.price, " +
            "volume = EXCLUDED.volume, " +
            "latest_trading_day = EXCLUDED.latest_trading_day, " +
            "previous_close = EXCLUDED.previous_close, " +
            "change = EXCLUDED.change, " +
            "change_percent = EXCLUDED.change_percent, " +
            "timestamp = EXCLUDED.timestamp";

    private static final String GET_ONE = "SELECT symbol, open, high, low, price, volume, latest_trading_day, " +
            "previous_close, change, change_percent, timestamp FROM quote WHERE symbol = ?";

    private static final String GET_ALL = "SELECT * FROM quote";

    private static final String DELETE = "DELETE FROM quote WHERE symbol = ?";

    private static final String DELETE_ALL = "DELETE FROM quote";

    public QuoteDao(Connection c) {
        this.c = c;
    }

    @Override
    public Quote save(Quote entity) throws IllegalArgumentException {

        if(entity.getSymbol() == null) {
            throw new IllegalArgumentException("The id (symbol) cannot be null.");
        }

        try(PreparedStatement statement = c.prepareStatement(UPSERT);) {
            statement.setString(1, entity.getSymbol());
            statement.setDouble(2, entity.getOpen());
            statement.setDouble(3, entity.getHigh());
            statement.setDouble(4, entity.getLow());
            statement.setDouble(5, entity.getPrice());
            statement.setInt(6, entity.getVolume());
            statement.setDate(7, entity.getLatestTradingDay());
            statement.setDouble(8, entity.getPreviousClose());
            statement.setDouble(9, entity.getChange());
            statement.setString(10, entity.getChangePercent());
            statement.setTimestamp(11, entity.getTimestamp());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Optional<Quote> findById(String s) throws IllegalArgumentException {

        if(s == null) {
            throw new IllegalArgumentException("The id (symbol) cannot be null.");
        }

        Quote quote = new Quote();
        try(PreparedStatement statement = c.prepareStatement(GET_ONE);) {
            statement.setString(1, s);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                quote.setSymbol(resultSet.getString("symbol"));
                quote.setOpen(resultSet.getDouble("open"));
                quote.setHigh(resultSet.getDouble("high"));
                quote.setLow(resultSet.getDouble("low"));
                quote.setPrice(resultSet.getDouble("price"));
                quote.setVolume(resultSet.getInt("volume"));
                quote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
                quote.setPreviousClose(resultSet.getDouble("previous_close"));
                quote.setChange(resultSet.getDouble("change"));
                quote.setChangePercent(resultSet.getString("change_percent"));
                quote.setTimestamp(resultSet.getTimestamp("timestamp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return Optional.of(quote);
    }

    @Override
    public Iterable<Quote> findAll() {
        ArrayList<Quote> quotes = new ArrayList<>();
        try(PreparedStatement statement = c.prepareStatement(GET_ALL);) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Quote quote = new Quote();
                quote.setSymbol(resultSet.getString("symbol"));
                quote.setOpen(resultSet.getDouble("open"));
                quote.setHigh(resultSet.getDouble("high"));
                quote.setLow(resultSet.getDouble("low"));
                quote.setPrice(resultSet.getDouble("price"));
                quote.setVolume(resultSet.getInt("volume"));
                quote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
                quote.setPreviousClose(resultSet.getDouble("previous_close"));
                quote.setChange(resultSet.getDouble("change"));
                quote.setChangePercent(resultSet.getString("change_percent"));
                quote.setTimestamp(resultSet.getTimestamp("timestamp"));
                quotes.add(quote);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return quotes;
    }

    @Override
    public void deleteById(String s) throws IllegalArgumentException {

        if(s == null) {
            throw new IllegalArgumentException("The id (symbol) cannot be null.");
        }

        try(PreparedStatement statement = c.prepareStatement(DELETE);) {
            statement.setString(1, s);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = c.prepareStatement(DELETE_ALL);) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
