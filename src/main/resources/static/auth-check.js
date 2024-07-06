// auth-check.js

// Ensure Firebase is available
if (typeof firebase !== 'undefined') {
  // Initialize Firebase (Add your Firebase project configuration)
    const firebaseConfig = {
        apiKey: "AIzaSyC7qzfmew33btIrH4NQNShcMgFSm7SYR04",
        authDomain: "stockup-dc330.firebaseapp.com",
        projectId: "stockup-dc330",
        storageBucket: "stockup-dc330.appspot.com",
        messagingSenderId: "419133983663",
        appId: "1:419133983663:web:fd7fb320cbf625ba1ebd5a",
        measurementId: "G-42ZJ1F19GB"
    };

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);

  // Check login status
  firebase.auth().onAuthStateChanged(function(user) {
    if (!user) {
      // No user is signed in, redirect to login page
      window.location.href = "login.html";
    }
  });
} else {
  console.error('Firebase SDK not loaded');
}
