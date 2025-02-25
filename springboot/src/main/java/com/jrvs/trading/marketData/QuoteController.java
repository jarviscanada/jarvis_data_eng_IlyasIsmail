package com.jrvs.trading.marketData;

import com.jrvs.trading.ResponseExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
