import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from "react-router-dom";

import './App.css';

import Login from './authentication/Login';
import Dashboard from './Dashboard';
import About from './About';
import LoginUser from './authentication/LoginUser';


function App() {
  

  const loginSuccess = (token) => {
    console.log("Login Success: token=", token);
  }

  return (
    <Router>
    <div>
      <ul>
        <li>
          <Link to="/login">Login</Link>
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
        <Route exact path="/login">
          <LoginUser setToken={loginSuccess}/>
        </Route>
        <Route path="/dashboard">
          <Dashboard />
        </Route>
        <Route path="/about">
          <About />
        </Route>
      </Switch>
    </div>
    </Router>
  )  
}

export default App;
