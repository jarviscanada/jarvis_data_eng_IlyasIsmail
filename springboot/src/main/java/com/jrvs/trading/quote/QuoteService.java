package com.jrvs.trading.quote;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrvs.trading.marketData.MarketDataDao;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {

    /**
     * Find an IexQuote
     * @param ticker
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */

    private MarketDataDao dao;
    private QuoteDao quoteDao;
    private OkHttpClient client;

    @Autowired
    public QuoteService(MarketDataDao dao, QuoteDao quoteDao) {
        this.dao = dao;
        this.quoteDao = quoteDao;
    }

    public void updateMarketData() {
        List<Quote> dbQuotes = quoteDao.findAll();

        client = new OkHttpClient();

        for(Quote quote : dbQuotes) {
            Request request =  new Request.Builder()
                    .url("https://localhost:3000/quotes/quote/" + quote.ticker)
                    .build();

            Call call = client.newCall(request);

            try (Response response = call.execute()) {

                if(!response.isSuccessful()) {
                    throw new DataAccessException("Unable to retrieve data, please contact an admin.") {
                        @Override
                        public String getMessage() {
                            return super.getMessage();
                        }
                    };
                }

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body().string());
                response.close();

                if(jsonNode.at("ticker").asText().isEmpty()) {
                    throw new IllegalArgumentException("Invalid symbol or too many requests.");
                }

                Quote vantageQuote = new Quote();
                vantageQuote.setTicker(jsonNode.at("ticker").asText());
                vantageQuote.setOpen(jsonNode.at("open").asDouble());
                vantageQuote.setHigh(jsonNode.at("high").asDouble());
                vantageQuote.setLow(jsonNode.at("low").asDouble());
                vantageQuote.setPrice(jsonNode.at("price").asDouble());
                vantageQuote.setVolume(jsonNode.at("volume").asInt());
                vantageQuote.setLatestTradingDay(Date.valueOf(jsonNode.at("latest_trading_day").asText()));
                vantageQuote.setPreviousClose(jsonNode.at("previous_close").asDouble());
                vantageQuote.setChange(jsonNode.at("change").asDouble());
                vantageQuote.setChangePercent(jsonNode.at("change_percent").asText());

                quoteDao.save(vantageQuote);
            } catch (IOException e) {
                //logger.error("There was an input/output error when trying to fetch quote info from the API.", e);
            }
        }
    }

    public List<Quote> saveQuotes(List<String> tickers) {
        List<Quote> quotes = dao.findAllById(tickers);
        for(Quote quote : quotes) {
            quoteDao.save(quote);
        }
        return quotes;
    }

    public Quote findQuoteByTicker(String ticker) {

        Optional<Quote> quote;

        if ((quote = dao.findById(ticker)).isPresent()) {
            return quote.get();
        } else {
            throw new IllegalArgumentException("Invalid symbol or too many requests.");
        }
    }

    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    public List<Quote> findAllQuotes() {
        return quoteDao.findAll();
    }
}