import './Login.css';
import PropTypes from 'prop-types';

import {useState} from 'react';
import { useHistory } from 'react-router-dom';


const sleep = (milliseconds) => {
  return new Promise(resolve => setTimeout(resolve, milliseconds))
}

async function loginUser(credentials) {
  const mock = false
  if (mock) {
    sleep(2000).then(() => 
       "123"
    )
        
  } else {
    return fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    })
      .then(data => data.json())
  }
}

 
function LoginUser({setToken}) {
    const [username, setUserName] = useState();
    const [password, setPassword] = useState();

    const history = useHistory();

    const handleSubmit = async e => {
      e.preventDefault();
      const token = await loginUser({
        username,
        password
      });
      console.log("token=", token)
      setToken(token);

      history.push('/dashboard');
    }
    
    return (
      
        <div className="login-wrapper">
          <h1>Please Log In</h1>
          <form onSubmit={handleSubmit}>
            <label>
              <p>Username</p>
              <input type="text" onChange={e => setUserName(e.target.value)}/>
            </label>
            <label>
              <p>Password</p>
              <input type="password" onChange={e => setPassword(e.target.value)}/>
            </label>
            <div>
              <button type="submit">Submit</button>
            </div>
          </form>
        </div>
      
    );

}

LoginUser.propTypes = {
  setToken: PropTypes.func.isRequired
}

export default LoginUser;