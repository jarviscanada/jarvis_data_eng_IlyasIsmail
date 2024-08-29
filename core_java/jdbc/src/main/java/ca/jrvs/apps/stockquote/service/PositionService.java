package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.Position;
import ca.jrvs.apps.stockquote.dao.PositionDao;

public class PositionService {

    private PositionDao dao;
    private QuoteService service;

    public PositionService(PositionDao dao, QuoteService service) {
        this.dao = dao;
        this.service = service;
    }

    public Position buy(String symbol, int numberOfShares, double price) {
        int availableShares = service.fetchQuoteDataFromAPI(symbol).get().getVolume();

        if(numberOfShares > availableShares) {
            numberOfShares = availableShares;
        }

        Position position = dao.findById(symbol).get();

        if(position.getSymbol() != null) {
            position.setNumOfShares(position.getNumOfShares() + numberOfShares);
            position.setValuePaid(position.getValuePaid() + (numberOfShares * price));
        } else {
            position = new Position();
            position.setSymbol(symbol);
            position.setNumOfShares(numberOfShares);
            position.setValuePaid(numberOfShares * price);
        }



        return dao.save(position);
    }

    public void sell(String symbol) {
        dao.deleteById(symbol);
    }
}
