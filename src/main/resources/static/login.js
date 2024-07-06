import { initializeApp } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-app.js";
import { getAnalytics } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-analytics.js";
import { getAuth, signInWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-auth.js";
import { getFirestore, collection, query, where, getDocs } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-firestore.js";

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
const analytics = getAnalytics(app);
const auth = getAuth(app);
const db = getFirestore(app);

const loginForm = document.getElementById("loginForm");
loginForm.addEventListener("submit", function(event) {
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    signInWithEmailAndPassword(auth, email, password)
        .then((userCredential) => {
            // Signed in
            const user = userCredential.user;
            console.log("User Logged In successfully");

            // Fetch user details from Firestore using email
            fetchUserDetails(email);
            alert("Login successful!");

        })
        .catch((error) => {
            const errorMessage = error.message;
            alert(errorMessage);
        });
});

async function fetchUserDetails(email) {
    const usersRef = collection(db, "users");
    const q = query(usersRef, where("email", "==", email));
    const querySnapshot = await getDocs(q);

    if (!querySnapshot.empty) {
        querySnapshot.forEach((doc) => {
            const userData = doc.data();
            console.log("User data:", userData);
            // Store user data in local storage
            localStorage.setItem("userData", JSON.stringify(userData));
            // Redirect to the dashboard or any other page
            window.location.href = "product.html";
        });
    } else {
        console.error("No user document found with the provided email!");
    }
}