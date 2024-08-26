package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.Quote;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class QuoteHttpHelper {

    private OkHttpClient client;
    private final String apiKey = "key";

    public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {
        Request request = new Request.Builder()
                .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json")
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .build();

        client = new OkHttpClient();
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            response.close();

            if(jsonNode.at("/Global Quote/01. symbol").asText().isEmpty()) {
                throw new IllegalArgumentException("Invalid symbol.");
            }

            Quote quote = new Quote();
            quote.setSymbol(jsonNode.at("/Global Quote/01. symbol").asText());
            quote.setOpen(jsonNode.at("/Global Quote/02. open").asDouble());
            quote.setHigh(jsonNode.at("/Global Quote/03. high").asDouble());
            quote.setLow(jsonNode.at("/Global Quote/04. low").asDouble());
            quote.setPrice(jsonNode.at("/Global Quote/05. price").asDouble());
            quote.setVolume(jsonNode.at("/Global Quote/06. volume").asInt());
            quote.setLatestTradingDay(Date.valueOf(jsonNode.at("/Global Quote/07. latest trading day").asText()));
            quote.setPreviousClose(jsonNode.at("/Global Quote/08. previous close").asDouble());
            quote.setChange(jsonNode.at("/Global Quote/09. change").asDouble());
            quote.setChangePercent(jsonNode.at("/Global Quote/10. change percent").asText());

            return quote;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Quote();
    }
}
