import { initializeApp } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-app.js";

const firebaseConfig = {
    apiKey: "AIzaSyBkuX4fIYAxyYsJ0KpCh96S4E6IRH2Zh3c",
    authDomain: "stockup-7cdb3.firebaseapp.com",
    projectId: "stockup-7cdb3",
    storageBucket: "stockup-7cdb3.appspot.com",
    messagingSenderId: "368711125669",
    appId: "1:368711125669:web:ddecaeea1fa70e774fdb4c",
    measurementId: "G-D3C222YDPN"
};

const app = initializeApp(firebaseConfig);

// Get user data from local storage
const userData = JSON.parse(localStorage.getItem('userData'));

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
async function fetchStockPrices() {
    const stockSymbols = ['AAPL', 'GOOGL', 'NSQ'];
    const stockPriceElements = {
        'AAPL': document.getElementById('st_price1'),
        'GOOGL': document.getElementById('st_price2'),
        'NSQ': document.getElementById('st_price3'),
    };

    for (const symbol of stockSymbols) {
        try {
            const response = await fetch('http://localhost:8080/getStockDetails', {
                method: 'GET',
                headers: {
                    'accept': '*/*',
                    'symbol': symbol
                }
            });
            if (response.ok) {
                const stock = await response.json();
                stockPriceElements[symbol].innerText = stock.currentPrice;  // Assuming the stock object has a price field
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

// Attach event listener to the refresh button
document.querySelector('a[href="/"]').addEventListener('click', (event) => {
    event.preventDefault();
    fetchStockPrices();
});

// Execute fetchStockPrices on page load
window.onload = function () {
    fetchStockPrices();
};
