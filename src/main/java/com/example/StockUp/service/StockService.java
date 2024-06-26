package com.example.StockUp.service;

import com.example.StockUp.object.Person;
import com.example.StockUp.object.Stock;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class StockService {

    public Stock getStockDetails(String symbol) throws ExecutionException, InterruptedException {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection("stock").document(symbol);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        Stock stock = null;
        if (document.exists()) {
            stock = document.toObject(Stock.class);

        }
        return stock;
    }
    public String saveStockDetails(Stock stock) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("stock").document(stock.getSymbol()).set(stock);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public List<String> getAllStockSymbols() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference stocks = dbFirestore.collection("stock");
        ApiFuture<QuerySnapshot> querySnapshot = stocks.get();

        return querySnapshot.get().getDocuments().stream()
                .map(DocumentSnapshot::getId)
                .collect(Collectors.toList());
    }
}
