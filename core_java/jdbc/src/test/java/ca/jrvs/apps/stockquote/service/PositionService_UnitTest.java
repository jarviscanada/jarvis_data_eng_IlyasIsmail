package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Position;
import ca.jrvs.apps.stockquote.Quote;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PositionService_UnitTest {

    PositionService positionService;
    Position position;
    PositionDao positionDao;
    QuoteService quoteService;

    @Before
    public void setUp() throws Exception {
        positionDao = mock(PositionDao.class);
        quoteService = mock(QuoteService.class);
        positionService = new PositionService(positionDao, quoteService);

        position = new Position();
        position.setSymbol("MSFT");
        position.setValuePaid(160);
        position.setNumOfShares(500);

        Quote quote = new Quote();
        quote.setVolume(5000);
        quote.setSymbol("MSFT");
        quote.setPrice(160);
        Optional<Quote> temp = Optional.of(quote);

        when(positionDao.save(position)).thenReturn(position);
        when(quoteService.fetchQuoteDataFromAPI("MSFT")).thenReturn(temp);
    }

    @Test
    public void buy() {
        assertEquals(position, positionDao.save(position));
    }

    @Test
    public void buyOverVolume() {
        int desiredShares = 500000;
        int availableShares = quoteService.fetchQuoteDataFromAPI("MSFT").get().getVolume();

        if(desiredShares > availableShares) {
            position.setNumOfShares(availableShares);
        } else {
            position.setNumOfShares(desiredShares);
        }

        assertEquals(position.getNumOfShares(), availableShares);
    }

    @Test
    public void sell() {
        positionService.sell("MSFT");
        verify(positionDao).deleteById("MSFT");
    }
}