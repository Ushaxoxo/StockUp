// auth-check.js

// Ensure Firebase is available
if (typeof firebase !== 'undefined') {
  // Initialize Firebase (Add your Firebase project configuration)
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
