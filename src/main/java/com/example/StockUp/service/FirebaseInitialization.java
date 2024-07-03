package com.example.StockUp.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class FirebaseInitialization {

    @PostConstruct
    public void initialize(){
       try {
           FileInputStream serviceAccount =
                   new FileInputStream("src/blank.json");

           FirebaseOptions options = new FirebaseOptions.Builder()
                   .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                   .setDatabaseUrl("https://stockup-7cdb3.firebaseio.com")
                   .build();

           FirebaseApp.initializeApp(options);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}

