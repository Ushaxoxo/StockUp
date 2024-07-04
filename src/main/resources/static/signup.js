import { initializeApp } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-app.js";
import { getAnalytics } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-analytics.js";

import {
    getAuth,
    createUserWithEmailAndPassword,
} from "https://www.gstatic.com/firebasejs/10.12.2/firebase-auth.js";

const firebaseConfig = {
    apiKey: "AIzaSyBkuX4fIYAxyYsJ0KpCh96S4E6IRH2Zh3c",
    authDomain: "stockup-7cdb3.firebaseapp.com",
    projectId: "stockup-7cdb3",
    storageBucket: "stockup-7cdb3.appspot.com",
    messagingSenderId: "368711125669",
    appId: "1:368711125669:web:ddecaeea1fa70e774fdb4c",
    measurementId: "G-D3C222YDPN"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);
const auth = getAuth();
const submit = document.getElementById("submit");

submit.addEventListener("click", function (event) {
    event.preventDefault();
    // Inputs
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const username = document.getElementById("username").value;
    const fname = document.getElementById("fname").value;
    const lname = document.getElementById("lname").value;

    createUserWithEmailAndPassword(auth, email, password)
        .then((userCredential) => {
            // Signed up
            const user = userCredential.user;
            console.log("Successfully Created");

            // Call backend to save user details
            saveUserDetails(email, username, fname, lname);

            window.location.href = "product.html";
        })
        .catch((error) => {
            const errorCode = error.code;
            const errorMessage = error.message;
            console.error("Error creating user:", errorCode, errorMessage);
        });
});

function saveUserDetails(email, username, fname, lname) {
    const person = {
        email: email,
        username: username,
        name: `${fname} ${lname}`,
        score: 0
    };

    fetch("http://localhost:8080/createUser", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(person),
    })
        .then((response) => response.json())
        .then((data) => {
            console.log("User details saved:", data);
        })
        .catch((error) => {
            console.error("Error saving user details:", error);
            displayError(error.message);
        });
}
function displayError(errorMessage) {
    const errorElement = document.getElementById("error");
    errorElement.textContent = errorMessage;
    errorElement.style.display = "block";
}
