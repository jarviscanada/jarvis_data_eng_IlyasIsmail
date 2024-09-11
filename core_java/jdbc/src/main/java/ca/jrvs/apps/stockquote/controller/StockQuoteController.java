package ca.jrvs.apps.stockquote.controller;

import ca.jrvs.apps.stockquote.Position;
import ca.jrvs.apps.stockquote.Quote;
import ca.jrvs.apps.stockquote.dao.PositionDao;
import ca.jrvs.apps.stockquote.service.PositionService;
import ca.jrvs.apps.stockquote.service.QuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.stream.Stream;

public class StockQuoteController {

    private final QuoteService quoteService;
    private final PositionService positionService;
    private final PositionDao positionDao;
    private final Logger logger = LoggerFactory.getLogger(StockQuoteController.class);

    public StockQuoteController(QuoteService quoteService, PositionService positionService, PositionDao positionDao) {
        this.quoteService = quoteService;
        this.positionService = positionService;
        this.positionDao = positionDao;
    }

    public void initClient() {
        Scanner intScanner = new Scanner(System.in);
        System.out.println("--------------------------------------------");
        System.out.println("INPUT 1 TO VIEW A STOCK: ");
        System.out.println("INPUT 2 TO VIEW YOUR OWNED STOCK: ");
        System.out.println("INPUT 3 TO VIEW ALL YOUR OWNED STOCKS: ");
        System.out.println("INPUT 0 TO EXIT");
        System.out.println("--------------------------------------------");

        int input;
        Scanner stringScanner = new Scanner(System.in);
        String symbol;

        do {
            while (!intScanner.hasNextInt()) {
                logger.error("PLEASE INPUT A NUMBER FROM 0 - 3");
                intScanner.next();
            }
            input = intScanner.nextInt();

            if (input > 3 || input < 0) {
                logger.error("PLEASE INPUT A NUMBER FROM 0 - 3");
            }
        } while (input > 3 || input < 0);

        while(input != 0) {

            switch(input) {
                case 1:
                    System.out.println("INPUT THE SYMBOL OF THE STOCK YOU WANT TO VIEW: ");
                    symbol = stringScanner.nextLine();
                    try {
                        Quote quote = quoteService.fetchQuoteDataFromAPI(symbol).get();
                        System.out.println(quote);
                        System.out.println("INPUT 'Y' IF YOU WOULD LIKE TO PURCHASE THIS STOCK: ");
                        String choice = stringScanner.nextLine();
                        if(choice.equalsIgnoreCase("y")) {
                            System.out.println("HOW MANY STOCKS WOULD YOU LIKE TO PURCHASE?: ");

                            int amount;

                            do {
                                while (!intScanner.hasNextInt()) {
                                    logger.error("PLEASE INPUT A POSITIVE NUMBER");
                                    intScanner.next();
                                }
                                amount = intScanner.nextInt();

                                if (amount <= 0) {
                                    logger.error("YOU CANNOT PURCHASE NEGATIVE STOCKS, PLEASE INPUT A POSITIVE NUMBER");
                                }

                            } while (amount <= 0);

                            positionService.buy(quote.getSymbol(), amount, quote.getPrice());
                        }
                    } catch (IllegalArgumentException e) {
                        logger.error("INVALID SYMBOL OR TOO MANY REQUESTS.", e);
                    }
                    break;
                case 2:
                    System.out.println("INPUT THE SYMBOL OF THE STOCK YOU WANT TO VIEW: ");
                    symbol = stringScanner.nextLine();
                    try {
                        Position position = positionDao.findById(symbol).get();
                        Quote quote = quoteService.fetchQuoteDataFromAPI(symbol).get();
                        System.out.println(position);
                        System.out.println(quote);
                        double currValue = BigDecimal.valueOf(quote.getPrice() * position.getNumOfShares()).setScale(2, RoundingMode.HALF_UP).doubleValue();
                        if(position.getValuePaid() < currValue) {
                            System.out.println("SELLING YOUR " + position.getSymbol() + " STOCKS WILL RESULT IN A PROFIT OF $" + BigDecimal.valueOf(currValue - position.getValuePaid()).setScale(2, RoundingMode.HALF_UP) + ".");
                        } else if(position.getValuePaid() > currValue) {
                            System.out.println("SELLING YOUR " + position.getSymbol() + " STOCKS WILL RESULT IN A LOSS OF $" + BigDecimal.valueOf(position.getValuePaid() - currValue).setScale(2, RoundingMode.HALF_UP) + ".");
                        }
                        System.out.println("INPUT 'Y' IF YOU WOULD LIKE TO SELL ALL " + symbol + " STOCKS: ");
                        String choice = stringScanner.nextLine();
                        if(choice.equalsIgnoreCase("y")) {
                            positionService.sell(symbol);
                        }
                    } catch (IllegalArgumentException e) {
                        logger.error("INVALID SYMBOL OR TOO MANY REQUESTS.", e);
                    }
                    break;
                case 3:
                    Iterable<Position> positions = positionDao.findAll();
                    Stream.of(positions).forEach(System.out::println);
                    break;
            }

            System.out.println("--------------------------------------------");
            System.out.println("INPUT 1 TO VIEW A STOCK: ");
            System.out.println("INPUT 2 TO VIEW A SPECIFIC OWNED STOCK: ");
            System.out.println("INPUT 3 TO VIEW ALL YOUR OWNED STOCKS: ");
            System.out.println("INPUT 0 TO EXIT");
            System.out.println("--------------------------------------------");

            do {
                while (!intScanner.hasNextInt()) {
                    logger.error("PLEASE INPUT A NUMBER FROM 0 - 3");
                    intScanner.next();
                }
                input = intScanner.nextInt();

                if (input > 3 || input < 0) {
                    logger.error("PLEASE INPUT A NUMBER FROM 0 - 3");
                }
            } while (input > 3 || input < 0);

        }
    }
}
