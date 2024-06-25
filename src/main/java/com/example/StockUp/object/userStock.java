package com.example.StockUp.object;

public class userStock {
    private String userName;
    private String symbol;
    private float guessedPrice;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getGuessedPrice() {
        return guessedPrice;
    }

    public void setGuessedPrice(float guessedPrice) {
        this.guessedPrice = guessedPrice;
    }

}
