package ca.jrvs.apps.jdbc;

import okhttp3.*;

import java.io.IOException;


public class JDBCExecutor {
    public static void main(String[] args) {
        /*DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "pass");
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
        }*/

        OkHttpClient client = new OkHttpClient();
        String symbol = "MSFT";
        String apiKey = "apikey";
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
