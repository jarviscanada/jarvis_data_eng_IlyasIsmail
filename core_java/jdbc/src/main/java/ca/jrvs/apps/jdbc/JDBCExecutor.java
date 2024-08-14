package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "pass");
        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = customerDAO.findById(10000);
            System.out.println(customer.toString());
            customer.setFirstName("Example");
            customer = customerDAO.update(customer);
            System.out.println(customer.toString());
            customerDAO.delete(customer.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
