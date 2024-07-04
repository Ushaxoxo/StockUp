package com.example.StockUp.controller;

import com.example.StockUp.object.Person;
import com.example.StockUp.object.Stock;
import com.example.StockUp.object.userStock;
import com.example.StockUp.service.StockService;
import com.example.StockUp.service.UserService;
import com.example.StockUp.service.userStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin("*")
public class AppController {
    @Autowired
    UserService userService;
    @Autowired
    StockService stockService;
    @Autowired
    userStockService userstockService;
    
    @GetMapping("/")
    public String index() {
        return "index";  // this should correspond to src/main/resources/templates/index.html
    }


    @GetMapping("/getUserDetails")
    public Person getUserDetails(@RequestHeader String name) throws ExecutionException, InterruptedException {
        return userService.getUserDetails(name);
    }
    @PostMapping("/createUser")
    public String createUser(@RequestBody Person person) throws ExecutionException, InterruptedException {
        return userService.saveUserDetails(person);
    }

    @PutMapping("/updateUser")
    public String updateUser(@RequestBody Person person) throws ExecutionException, InterruptedException {
        return userService.saveUserDetails(person);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestHeader String name) throws ExecutionException, InterruptedException {
        return userService.deleteUser(name);
    }

    //STOCK CONTROLLER


    @GetMapping("/getStockDetails")
    public Stock getStockDetails(@RequestHeader String symbol) throws ExecutionException, InterruptedException {
        return stockService.getStockDetails(symbol);
    }
    @PutMapping("/updateStock")
    public String updateStock(@RequestBody Stock stock) throws ExecutionException, InterruptedException {
        return stockService.saveStockDetails(stock);
    }

    //StockUser Controller

    @PostMapping("/createGuess")
    public String createGuess(@RequestBody userStock guess) throws ExecutionException, InterruptedException {
        return userstockService.saveGuessDetails(guess);
    }

    @GetMapping("/getGuessDetails") // you gotta pass id = "<stocksymbol>"+"-"+"<username>"
    public userStock getGuessDetails(@RequestHeader String id) throws ExecutionException, InterruptedException {
        return userstockService.getGuessDetails(id);
    }
    @PutMapping("/updateGuess")
    public String updateGuess(@RequestBody userStock guess) throws ExecutionException, InterruptedException {
        return userstockService.saveGuessDetails(guess);
    }

    @DeleteMapping("/deleteGuess")
    public String deleteGuess(@RequestHeader String id) throws ExecutionException, InterruptedException {
        return userstockService.deleteGuess(id);
    }

}
