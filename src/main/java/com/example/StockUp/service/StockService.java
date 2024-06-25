package com.example.StockUp.service;

import com.example.StockUp.object.Person;
import com.example.StockUp.object.Stock;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
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
}
