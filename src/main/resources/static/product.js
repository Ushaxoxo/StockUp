import { initializeApp } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-app.js";

const firebaseConfig = {
    apiKey: "AIzaSyC7qzfmew33btIrH4NQNShcMgFSm7SYR04",
    authDomain: "stockup-dc330.firebaseapp.com",
    projectId: "stockup-dc330",
    storageBucket: "stockup-dc330.appspot.com",
    messagingSenderId: "419133983663",
    appId: "1:419133983663:web:fd7fb320cbf625ba1ebd5a",
    measurementId: "G-42ZJ1F19GB"
};

const app = initializeApp(firebaseConfig);

// Get user data from local storage
const userData = JSON.parse(localStorage.getItem('userData'));
if (userData && userData.name) {
    document.getElementById("user_name").innerText = userData.name;
}
document.getElementById("guessForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const stockSymbol = document.getElementById("stock").value;
    const guessedPrice = document.getElementById("guess").value;
    const userName = userData.username;

    const guessData = {
        userName: userName,
        symbol: stockSymbol,
        guessedPrice: Number(guessedPrice),
        score: 0,
    };

    try {
        const response = await fetch('http://localhost:8080/createGuess', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(guessData)
        });

        if (response.ok) {
            alert("Guess submitted successfully!");
        } else {
            alert("Failed to submit guess");
        }
    } catch (e) {
        console.error("Error submitting guess: ", e);
    }
});

// Function to fetch and display stock prices
document.addEventListener('DOMContentLoaded', (event) => {
    fetchStockPrices();
    fetchUserScores();
});

async function fetchStockPrices() {
    const stockSymbols = [ 'AAPL', 'GOOGL', 'NSQ','MSFT', 'TATASTEEL'];
    const stockPriceElements = {
        'AAPL': document.getElementById('st_price1'),
        'GOOGL': document.getElementById('st_price2'),
        'NSQ': document.getElementById('st_price3'),
        'MSFT': document.getElementById('st_price4'),
        'TATASTEEL': document.getElementById('st_price5')
    };

    for (const symbol of stockSymbols) {
        try {
            const response = await fetch(`http://localhost:8080/getStockDetails`, {
                method: 'GET',
                headers: {
                    'accept': '*/*',
                    'symbol': symbol
                }
            });

            if (response.ok) {
                const stock = await response.json();
                stockPriceElements[symbol].innerText = stock.currentPrice;  // Assuming the stock object has a currentPrice field
            } else {
                console.error(`Failed to fetch stock details for ${symbol}`);
                stockPriceElements[symbol].innerText = "Error";
            }
        } catch (e) {
            console.error(`Error fetching stock details for ${symbol}: `, e);
            stockPriceElements[symbol].innerText = "Error";
        }
    }
}

async function fetchUserScores() {
    const userName = userData.username;

    try {
        const response = await fetch(`http://localhost:8080/getUserScores`, {
            method: 'GET',
            headers: {
                'accept': '*/*',
                'userName': userName
            }
        });

        if (response.ok) {
            const userStocks = await response.json();
            displayUserScores(userStocks);
        } else {
            console.error(`Failed to fetch user scores for ${userName}`);
        }
    } catch (e) {
        console.error(`Error fetching user scores for ${userName}: `, e);
    }
}

function displayUserScores(userStocks) {
    let totalScore = 0;

    userStocks.forEach(userStock => {
        totalScore += userStock.score;
    });

    console.log(`Total score for user: ${totalScore}`);

    // Check if the element exists before trying to update it
    const scoreElement = document.getElementById('totalScore');
    if (scoreElement) {
        scoreElement.innerText = `Total Score: ${totalScore}`;
    } else {
        console.error('Element with ID "totalScore" not found in the DOM');
    }
}

// Attach event listener to the refresh button
document.querySelector('a[href="/"]').addEventListener('click', (event) => {
    event.preventDefault();
    fetchStockPrices();
});

// Execute fetchStockPrices on page load
window.onload = function () {
    fetchStockPrices();
};
