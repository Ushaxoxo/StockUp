package com.example.StockUp.object;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;

public class StockPriceLog {
    @DocumentId
    private String id;
    private String symbol;
    private float price;
    private Timestamp timestamp;

    public StockPriceLog() {}

    public StockPriceLog(String symbol, float price, Timestamp timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
