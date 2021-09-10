import React, { useEffect, useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect
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
import MainNavigation from './MainNavigatoin';

function App() {
  const [authTokens, setAuthTokens] = useState();
  const setTokens = (data) => {
    if (data) {
      localStorage.setItem("tokens", JSON.stringify(data));
    } else {
      localStorage.removeItem("tokens");
    }
    setAuthTokens(data);
  }

  useEffect(() => {
    const tokensJsonStr = localStorage.getItem("tokens");
    console.log("App: useEffect(): tokensJsonStr:", tokensJsonStr)
    if (tokensJsonStr) {
      try {
        const tokens = JSON.parse(tokensJsonStr);
        console.log("App: useEffect(): tokens:", tokens)
        setAuthTokens(tokens);

      } catch(e) {
        console.log("App: useEffect(): exception:", e.message)
      }
      
    }
  }, [])

  return (
    <AuthContext.Provider value={{authTokens, setAuthTokens: setTokens}}>
    <Router>
    <div>
      <MainNavigation />
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
