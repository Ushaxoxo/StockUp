package com.example.StockUp.object;

import org.springframework.stereotype.Component;

@Component
public class Person {

    private String name;
    private int age;
    private int score;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Person(String name, int age, int score, String email, String username) {
        this.name = name;
        this.age = age;
        this.score = score;
        this.email = email;
        this.username = username;
    }

    private String username;
    public Person() {
        super();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}