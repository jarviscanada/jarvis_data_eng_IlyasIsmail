package com.jrvs.trading.marketData;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;

@Component
public class MarketDataDao {

    private static final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private OkHttpClient client;

    public Optional<Quote> findById(String ticker) {
        Request request =  new Request.Builder()
                .url("https://localhost:3000/quotes/quote/" + ticker)
                .build();

        client = getHttpClient();
        Call call = client.newCall(request);

        try (Response response = call.execute()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            response.close();

            if(jsonNode.at("ticker").asText().isEmpty()) {
                throw new IllegalArgumentException("Invalid symbol or too many requests.");
            }

            Quote quote = new Quote();
            quote.setTicker(jsonNode.at("ticker").asText());
            quote.setOpen(jsonNode.at("open").asDouble());
            quote.setHigh(jsonNode.at("high").asDouble());
            quote.setLow(jsonNode.at("low").asDouble());
            quote.setPrice(jsonNode.at("price").asDouble());
            quote.setVolume(jsonNode.at("volume").asInt());
            quote.setLatestTradingDay(Date.valueOf(jsonNode.at("latest_trading_day").asText()));
            quote.setPreviousClose(jsonNode.at("previous_close").asDouble());
            quote.setChange(jsonNode.at("change").asDouble());
            quote.setChangePercent(jsonNode.at("change_percent").asText());

            return Optional.of(quote);
        } catch (IOException e) {
            logger.error("There was an input/output error when trying to fetch quote info from the API.", e);
        }
        return Optional.empty();
    }

    /**
     * Get quotes from IEX
     * @param tickers is a list of tickers
     * @return a list of IexQuote objects
     * @throws IllegalArgumentException if a given ticker is invalid
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    public List<Quote> findAllById(Iterable<String> tickers) {

        List<Quote> quotes = new ArrayList<>();
        Request request =  new Request.Builder()
                .url("https://localhost:3000/quotes/quotes?tickers=" + tickers)
                .build();

        client = getHttpClient();
        Call call = client.newCall(request);

        try (Response response = call.execute()) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string()).get("quotes");
            response.close();

            if(jsonNode.isEmpty()) {
                throw new IllegalArgumentException("Invalid symbol or too many requests.");
            }

            if (jsonNode.isArray()) {
                for (final JsonNode objNode : jsonNode) {
                    Quote quote = new Quote();
                    quote.setTicker(jsonNode.at("ticker").asText());
                    quote.setOpen(jsonNode.at("open").asDouble());
                    quote.setHigh(jsonNode.at("high").asDouble());
                    quote.setLow(jsonNode.at("low").asDouble());
                    quote.setPrice(jsonNode.at("price").asDouble());
                    quote.setVolume(jsonNode.at("volume").asInt());
                    quote.setLatestTradingDay(Date.valueOf(jsonNode.at("latest_trading_day").asText()));
                    quote.setPreviousClose(jsonNode.at("previous_close").asDouble());
                    quote.setChange(jsonNode.at("change").asDouble());
                    quote.setChangePercent(jsonNode.at("change_percent").asText());
                    quotes.add(quote);
                }
            }
        } catch (IOException e) {
            logger.error("There was an input/output error when trying to fetch quote info from the API.", e);
        }
        return quotes;
    }

    /**
     * Execute a GET request and return http entity/body as a string
     * Tip: use EntitiyUtils.toString to process HTTP entity
     *
     * @param url resource URL
     * @return http response body or Optional.empty for 404 response
     * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
     */
    private Optional<String> executeHttpGet(String url) {
        Request request =  new Request.Builder()
                .url(url)
                .build();

        client = getHttpClient();
        Call call = client.newCall(request);

        try (Response response = call.execute()) {
            if (response.code() == 404) {
                return Optional.empty();
            } else if (response.code() != 200) {
                throw new DataRetrievalFailureException("Unexpected status code: " + response.code());
            }
            return Optional.of(response.toString());
        } catch (IOException e) {
            logger.error("There was an input/output error when trying to fetch quote info from the API.", e);
        }
        return Optional.empty();
    }

    /**
     * Borrow an HTTP client from the HttpClientConnectionManager
     * @return a HttpClient
     */
    private OkHttpClient getHttpClient() {
        client = new OkHttpClient();
        return client;
    }

}