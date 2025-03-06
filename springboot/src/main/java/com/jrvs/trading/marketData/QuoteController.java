package com.jrvs.trading.marketData;

import com.jrvs.trading.ResponseExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quote")
public class QuoteController {

    private QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping(path = "/vantage/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Quote getQuote(@PathVariable String ticker) {
        try {
            return quoteService.findQuoteByTicker(ticker);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/vantage/tickers")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void updateMarketData() {
        try {
            quoteService.updateMarketData();
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Quote putQuote(@RequestBody Quote quote) {
        try {
            return quoteService.saveQuote(quote);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @PostMapping(path = "/vantage/ticker/{ticker}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Quote createQuote(@PathVariable String ticker) {
        try {
            return quoteService.saveQuote(quoteService.findQuoteByTicker(ticker));
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/vantage/dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getDailyList() {
        try {
            return quoteService.findAllQuotes();
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
