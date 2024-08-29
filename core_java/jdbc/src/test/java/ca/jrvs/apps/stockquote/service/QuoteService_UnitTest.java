package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDao;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuoteService_UnitTest {

    QuoteService quoteService;
    QuoteHttpHelper quoteHttpHelper;
    QuoteDao quoteDao;

    @Before
    public void setUp() throws Exception {
        quoteDao = mock(QuoteDao.class);
        quoteHttpHelper = mock(QuoteHttpHelper.class);
        quoteService = new QuoteService(quoteDao, quoteHttpHelper);

        Quote quote = new Quote();
        quote.setSymbol("MSFT");
        quote.setPrice(150);
        when(quoteDao.save(quoteHttpHelper.fetchQuoteInfo("MSFT"))).thenReturn(quote);
    }

    @Test
    public void fetchQuoteDataFromAPI() {
        Quote sample = quoteService.fetchQuoteDataFromAPI("MSFT").get();
        assertNotNull(sample);
        assertEquals(sample.getSymbol(), "MSFT");
        assertEquals(sample.getPrice(), 150, 1);

    }
}