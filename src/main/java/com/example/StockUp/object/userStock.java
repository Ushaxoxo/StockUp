package com.example.StockUp.object;

import com.google.cloud.Timestamp;

public class userStock {
    private String userName;
    private String symbol;
    private float guessedPrice;
    private int guessedSeconds;
    private Timestamp guessedTime;
    private float score;

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

    public int getGuessedSeconds() {
        return guessedSeconds;
    }

    public void setGuessedSeconds(int guessedSeconds) {
        this.guessedSeconds = guessedSeconds;
    }

    public Timestamp getGuessedTime() {
        return guessedTime;
    }

    public void setGuessedTime(Timestamp guessedTime) {
        this.guessedTime = guessedTime;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
