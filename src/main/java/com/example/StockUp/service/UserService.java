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
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("users").document(person.getName()).set(person);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Person getUserDetails(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection("users").document(name);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Person person = null;
        if (document.exists()) {
            person = document.toObject(Person.class);

        }
        return person;
    }

    public String deleteUser(String name) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbFirestore.collection("users").document(name);
        ApiFuture<WriteResult> future = docRef.delete();
        return future.get().getUpdateTime().toString();

    }

    public void updateUserScore(String name, float additionalScore) throws ExecutionException, InterruptedException {
        Person person = getUserDetails(name);
        if (person != null) {
            person.setScore((int) (person.getScore() + additionalScore));
            saveUserDetails(person);
        }
    }
};
