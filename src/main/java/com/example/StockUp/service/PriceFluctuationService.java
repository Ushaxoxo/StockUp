package com.example.StockUp.service;

import com.example.StockUp.object.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

@Service
public class PriceFluctuationService {

    private final StockService stockService;
    private final Random random = new Random();

    @Autowired
    public PriceFluctuationService(StockService stockService) {
        this.stockService = stockService;
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updatePrices, 0, 5, TimeUnit.SECONDS);
    }

    private void updatePrices() {
        try {
            for (String symbol : stockService.getAllStockSymbols()) {
                Stock stock = stockService.getStockDetails(symbol);
                if (stock != null) {
                    float newPrice = getRandomPrice(stock.getCurrentPrice());
                    stock.setCurrentPrice(newPrice);
                    stockService.saveStockDetails(stock);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private float getRandomPrice(float currentPrice) {
        float change = currentPrice * (random.nextFloat() - 0.5f) * 0.1f; // ±5% change
        return currentPrice + change;
    }
}
