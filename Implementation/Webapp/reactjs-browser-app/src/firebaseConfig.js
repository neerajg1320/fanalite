import firebase from 'firebase';

const firebaseConfig = {
    apiKey: "AIzaSyBKnz_i81VJ8pl1cQXzDljMshlnymjoub4",
    authDomain: "fana-4e7be.firebaseapp.com",
    databaseURL: "https://fana-4e7be-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "fana-4e7be",
    storageBucket: "fana-4e7be.appspot.com",
    messagingSenderId: "622099335644",
    appId: "1:622099335644:web:ec9c57475623fc4ae66719",
    measurementId: "G-1CCGHBR154"
  };

firebase.initializeApp(firebaseConfig);
export const firebaseDatabase = firebase.database();


// Udemy: Andre, Yihua
export const firebaseAuth = firebase.auth();
const provider = new firebase.auth.GoogleAuthProvider()
provider.setCustomParameters({prompt: 'select_account'})

export const signInWithGoogle = () => firebaseAuth.signInWithPopup(provider)

export default firebase;
