import {useState, useEffect} from 'react';
import { signInWithGoogle, auth } from '../../firebaseConfig';

function Login({setToken}) {
  const [currentUser,setCurrentUser] = useState();

  useEffect(() => {
    const unsubscribe = auth.onAuthStateChanged( userAuth => {
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

            <button  onClick={() => auth.signOut()}>LOG OUT</button>
        </div>
        ) :
        <button onClick={signInWithGoogle}>SIGN IN WITH GOOGLE</button>
    }
    </div >
  );
}

export default Login;