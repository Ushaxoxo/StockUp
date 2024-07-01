package com.example.StockUp.service;

import com.example.StockUp.object.Stock;
import com.example.StockUp.object.userStock;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class userStockService {

    private final GuessScoringService guessScoringService;

    @Autowired
    public userStockService(GuessScoringService guessScoringService) {
        this.guessScoringService = guessScoringService;
    }

    public userStock getGuessDetails(String id) throws ExecutionException, InterruptedException {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection("userStock").document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        userStock userstock = null;
        if (document.exists()) {
            userstock = document.toObject(userStock.class);

        }
        return userstock;
    }

    public String saveGuessDetails(userStock guess) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        String id = guess.getSymbol() + '-' + guess.getUserName();

        guess.setGuessedTime(addSecondsToTimestamp(Timestamp.now(), guess.getGuessedSeconds()));
        float score = guessScoringService.calculateScore(guess);
        guess.setScore(score);

        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("userStock").document(id).set(guess);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }


    public String deleteGuess(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection("userStock").document(id);
        ApiFuture<WriteResult> future = docRef.delete();
        return future.get().getUpdateTime().toString();

    }

    private Timestamp addSecondsToTimestamp(Timestamp timestamp, int seconds) {
        long newSeconds = timestamp.getSeconds() + seconds;
        return Timestamp.ofTimeSecondsAndNanos(newSeconds, timestamp.getNanos());
    }
}
