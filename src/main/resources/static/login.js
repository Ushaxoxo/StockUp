import { initializeApp } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-app.js";
import { getAnalytics } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-analytics.js";
import { getAuth, signInWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-auth.js";
import { getFirestore, collection, query, where, getDocs } from "https://www.gstatic.com/firebasejs/10.12.2/firebase-firestore.js";

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