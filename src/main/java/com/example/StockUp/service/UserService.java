package com.example.StockUp.service;

import com.example.StockUp.object.Person;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    public String saveUserDetails(Person person) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("users").document(person.getUsername()).set(person);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Person getUserDetails(String username) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection("users").document(username);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Person person = null;
        if (document.exists()) {
            person = document.toObject(Person.class);

        }
        return person;
    }

    public String deleteUser(String username) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection("users").document(username);
        ApiFuture<WriteResult> future = docRef.delete();
        return future.get().getUpdateTime().toString();

    }

    public void updateUserScore(String username, float additionalScore) throws ExecutionException, InterruptedException {
        Person person = getUserDetails(username);
        if (person != null) {
            person.setScore((int) (person.getScore() + additionalScore));
            saveUserDetails(person);
        }
    }
};
