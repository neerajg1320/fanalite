import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";

import './App.css';

import Login from './authentication/Login';
import Dashboard from './pages/Dashboard';
import About from './pages/About';
import LoginUser from './authentication/LoginUser';
import Home from './pages/Home';

import { AuthContext } from './authentication/AuthContext';
import PrivateRoute from './PrivateRoute';
import SigninForm from './authentication/SigninForm';
import SignupForm from './authentication/SignupForm';

function App() {
  const loginSuccess = (token) => {
    console.log("Login Success: token=", token);
  }

  return (
    <AuthContext.Provider value={false}>
    <Router>
    <div>
      <ul>
        <li>
          <Link to="/">Home</Link>
        </li>
        <li>
          <Link to="/signin">SignIn</Link>
        </li>
        <li>
          <Link to="/signup">SignUp</Link>
        </li>
        <li>
          <Link to="/dashboard">Dashboard</Link>
        </li>
        <li>
          <Link to="/about">About</Link>
        </li>
      </ul>

      <hr />
      

      {/*
        A <Switch> looks through all its children <Route>
        elements and renders the first one whose path
        matches the current URL. Use a <Switch> any time
        you have multiple routes, but you want only one
        of them to render at a time
      */}
      <Switch>
        <Route exact path="/" component={Home} />
        {/* <Route path="/login">
          <LoginUser setToken={loginSuccess}/>
        </Route> */}
        <Route path="/signin" component={SigninForm} />
        <Route path="/signup" component={SignupForm} />
        <PrivateRoute path="/dashboard" component={Dashboard} />
        <Route path="/about" component={About} />
      </Switch>
    </div>
    </Router>
    </AuthContext.Provider>
  )  
}

export default App;
