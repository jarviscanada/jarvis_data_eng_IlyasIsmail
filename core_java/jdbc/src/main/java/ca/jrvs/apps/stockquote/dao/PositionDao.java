package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class PositionDao implements CrudDao<Position, String>{

    private Connection c;

    private final Logger logger = LoggerFactory.getLogger(PositionDao.class);

    private static final String UPSERT = "INSERT INTO position (symbol, number_of_shares, value_paid) " +
            "VALUES (?, ?, ?) " +
            "ON CONFLICT(symbol) " +
            "DO UPDATE SET " +
            "symbol = EXCLUDED.symbol, " +
            "number_of_shares = EXCLUDED.number_of_shares, " +
            "value_paid = EXCLUDED.value_paid";

    private static final String GET_ONE = "SELECT symbol, number_of_shares, value_paid FROM position WHERE symbol = ?";

    private static final String GET_ALL = "SELECT * FROM position";

    private static final String DELETE = "DELETE FROM position WHERE symbol = ?";

    private static final String DELETE_ALL = "DELETE FROM position";


    public PositionDao(Connection c) {
        this.c = c;
    }

    @Override
    public Position save(Position entity) throws IllegalArgumentException {

        if(entity.getSymbol() == null) {
            throw new IllegalArgumentException("The id (symbol) cannot be null.");
        }

        try(PreparedStatement statement = c.prepareStatement(UPSERT);) {
            statement.setString(1, entity.getSymbol());
            statement.setInt(2, entity.getNumOfShares());
            statement.setDouble(3, entity.getValuePaid());
            statement.execute();
        } catch (SQLException e) {
            logger.error("Error in saving/updating a position in the database.", e);
            throw new RuntimeException("There was an error accessing the database.", e);
        }
        return entity;
    }

    @Override
    public Optional<Position> findById(String s) throws IllegalArgumentException {

        if(s == null) {
            throw new IllegalArgumentException("The id (symbol) cannot be null.");
        }

        Position position = new Position();
        try(PreparedStatement statement = c.prepareStatement(GET_ONE);) {
            statement.setString(1, s);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                position.setSymbol(resultSet.getString("symbol"));
                position.setNumOfShares(resultSet.getInt("number_of_shares"));
                position.setValuePaid(resultSet.getDouble("value_paid"));
            }
        } catch (SQLException e) {
            logger.error("There was an error in retrieving a position from the database.", e);
            throw new RuntimeException("There was an error accessing the database.", e);
        }
        return Optional.of(position);
    }

    @Override
    public Iterable<Position> findAll() {
        ArrayList<Position> positions = new ArrayList<>();
        try(PreparedStatement statement = c.prepareStatement(GET_ALL);) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Position position = new Position();
                position.setSymbol(resultSet.getString("symbol"));
                position.setNumOfShares(resultSet.getInt("number_of_shares"));
                position.setValuePaid(resultSet.getDouble("value_paid"));
                positions.add(position);
            }
        } catch (SQLException e) {
            logger.error("There was an error in retrieving multiple positions from the database.", e);
            throw new RuntimeException("There was an error accessing the database.", e);
        }
        return positions;
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
            logger.error("There was an error in deleting an position from the database.", e);
            throw new RuntimeException("There was an error accessing the database.", e);
        }
    }

    @Override
    public void deleteAll() {
        try(PreparedStatement statement = c.prepareStatement(DELETE_ALL);) {
            statement.execute();
        } catch (SQLException e) {
            logger.error("There was an error deleting all positions from the database.", e);
            throw new RuntimeException("There was an error accessing the database.", e);
        }
    }
}
