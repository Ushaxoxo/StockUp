package com.example.StockUp.service;

import com.example.StockUp.object.Stock;
import com.example.StockUp.object.userStock;
import com.example.StockUp.object.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class GuessScoringService {

    @Value("${scoringMethod}")
    private String scoringMethod;

    private final StockService stockService;
    private final UserService userService;

    @Autowired
    public GuessScoringService(StockService stockService, UserService userService) {
        this.stockService = stockService;
        this.userService = userService;
    }

    public float calculateScore(userStock guess) throws ExecutionException, InterruptedException {
        float score;
        if ("static".equals(scoringMethod)) {
            score = scoreGuessStatic(guess);
        } else {
            score = scoreGuessDynamic(guess);
        }

        // Update the user's score
        updatePersonScore(guess.getUserName(), score);

        return score;
    }

    private float scoreGuessStatic(userStock guess) throws ExecutionException, InterruptedException {
        Stock stock = stockService.getStockDetails(guess.getSymbol());
        float closingPrice = stock.getClosingPrice();
        float guessedPrice = guess.getGuessedPrice();

        float percentageDifference = Math.abs(closingPrice - guessedPrice) / closingPrice * 100;
        float score = 100 - (percentageDifference * 2);

        return Math.max(score, 0);
    }

    private float scoreGuessDynamic(userStock guess) throws ExecutionException, InterruptedException {
        Stock stock = stockService.getStockDetails(guess.getSymbol());
        float currentPrice = stock.getCurrentPrice();
        float guessedPrice = guess.getGuessedPrice();
        int guessedSeconds = guess.getGuessedSeconds();

        float percentageDifference = Math.abs(currentPrice - guessedPrice) / currentPrice * 100;
        float baseScore = 100 - (percentageDifference * 2);
        float scoreMultiplier = 1 + (guessedSeconds * 0.01f);

        float finalScore = baseScore * scoreMultiplier;

        return Math.max(finalScore, 0);
    }

    private void updatePersonScore(String userName, float score) throws ExecutionException, InterruptedException {
        userService.updateUserScore(userName, score);
    }
}