package ca.jrvs.apps.stockquote;

public class Position {

    private String symbol; //id
    private int numOfShares;
    private double valuePaid; //total amount paid for shares

    public Position() {

    }

    public Position(String symbol, int numOfShares, double valuePaid) {
        this.symbol = symbol;
        this.numOfShares = numOfShares;
        this.valuePaid = valuePaid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getNumOfShares() {
        return numOfShares;
    }

    public void setNumOfShares(int numOfShares) {
        this.numOfShares = numOfShares;
    }

    public double getValuePaid() {
        return valuePaid;
    }

    public void setValuePaid(double valuePaid) {
        this.valuePaid = valuePaid;
    }

    @Override
    public String toString() {
        return "--------------------------------------------" + System.lineSeparator() +
                "Position: " + System.lineSeparator() +
                "Symbol: " + symbol + System.lineSeparator() +
                "Number of Shares: " + numOfShares + System.lineSeparator() +
                "Value Paid: " + valuePaid + System.lineSeparator();
    }
}
