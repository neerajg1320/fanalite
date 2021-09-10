import {useState, useEffect} from 'react';
import { signInWithGoogle, firebaseAuth } from '../../firebaseConfig';

function Login({setToken}) {
  const [currentUser,setCurrentUser] = useState();

  useEffect(() => {
    const unsubscribe = firebaseAuth.onAuthStateChanged( userAuth => {
      setCurrentUser(userAuth);
      if (userAuth) {
        setToken("Token")
      }
    });

    return () => { unsubscribe() }
  },[currentUser])

  return (
    <div className='login-wrapper'>
    {
        currentUser ?
        (<div>
            <div>
            <img src={currentUser.photoURL} />
            </div>
            <div>Name: {currentUser.displayName}</div>
            <div>Email: {currentUser.email}</div>

            <button  onClick={() => firebaseAuth.signOut()}>LOG OUT</button>
        </div>
        ) :
        <button onClick={signInWithGoogle}>SIGN IN WITH GOOGLE</button>
    }
    </div >
  );
}

export default Login;