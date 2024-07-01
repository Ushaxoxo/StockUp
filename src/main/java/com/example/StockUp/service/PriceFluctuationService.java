package com.example.StockUp.service;

import com.example.StockUp.object.Stock;
import com.example.StockUp.object.StockPriceLog;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PriceFluctuationService {

    private final StockService stockService;
    private final Random random = new Random();

    @Value("${logRetentionDurationMinutes}")
    private int logRetentionDurationMinutes;

    @Value("${priceUpdateIntervalSeconds}")
    private int priceUpdateIntervalSeconds;

    @Autowired
    public PriceFluctuationService(StockService stockService) {
        this.stockService = stockService;
    }

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updatePrices, 0, priceUpdateIntervalSeconds, TimeUnit.SECONDS);
    }

    private void updatePrices() {
        try {
            for (String symbol : stockService.getAllStockSymbols()) {
                Stock stock = stockService.getStockDetails(symbol);
                if (stock != null) {
                    float newPrice = getRandomPrice(stock.getCurrentPrice());
                    stock.setCurrentPrice(newPrice);
                    stockService.saveStockDetails(stock);
                    logPrice(stock);
                    removeOldLogs(symbol);
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

    private void logPrice(Stock stock) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String timestamp = Timestamp.now().toString();
        String documentId = timestamp + " - " + stock.getSymbol();
        StockPriceLog log = new StockPriceLog(stock.getSymbol(), stock.getCurrentPrice(), Timestamp.now());
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("stockPriceLogs").document(documentId).set(log);
        collectionsApiFuture.get();
    }

    private void removeOldLogs(String symbol) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Timestamp xMinutesAgo = Timestamp.ofTimeSecondsAndNanos(Timestamp.now().getSeconds() - (logRetentionDurationMinutes* 60L), 0);

        ApiFuture<QuerySnapshot> future = dbFirestore.collection("stockPriceLogs")
                .whereEqualTo("symbol", symbol)
                .whereLessThan("timestamp", xMinutesAgo)
                .get();

        List<String> oldLogsIds = future.get().getDocuments().stream()
                .map(document -> document.getId())
                .collect(Collectors.toList());

        for (String id : oldLogsIds) {
            dbFirestore.collection("stockPriceLogs").document(id).delete();
        }
    }

}
