import React, { useEffect, useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";

import './App.css';


import Dashboard from './pages/Dashboard';
import About from './pages/About';

import { AuthContext } from './authentication/AuthContext';
import PrivateRoute from './PrivateRoute';
import SigninForm from './authentication/SigninForm';
import SignupForm from './authentication/SignupForm';
import MainNavigation from './pages/MainNavigation';

import config from './config/default';


function App() {
  const [authToken, setAuthToken] = useState();

  // eslint-disable-next-line no-unused-vars
  const [localActive, setLocalActive] = useState(true);

  const getTokenFromLocalStorage = () => {
    return localStorage.getItem("token");
  };

  const setTokenToLocalStorage = (data) => {
    if (localActive) {
        if (data) {
            localStorage.setItem("token", JSON.stringify(data));
        } else {
            localStorage.removeItem("token");
        }
    }

    setAuthToken(data);
  };



  useEffect(() => {
      const setupTokenFromLocalStorage = () => {
          const tokensJsonStr = getTokenFromLocalStorage();

          if (config.debug.active) {
              console.log("App: useEffect(): tokensJsonStr:", tokensJsonStr)
          }

          if (tokensJsonStr) {
              try {
                  const token = JSON.parse(tokensJsonStr);
                  if (config.debug.active) {
                      console.log("App: useEffect(): token:", token);
                  }
                  setAuthToken(token);

              } catch(e) {
                  console.log("App: useEffect(): exception:", e.message);
              }
          } else {
              setAuthToken("invalid");
          }
      };
    setupTokenFromLocalStorage();
  }, []);

  return (
    <AuthContext.Provider value={{authTokens: authToken, setAuthTokens: setTokenToLocalStorage}}>
    <Router>
    <div>
      <MainNavigation />
      <div style={{margin:"20px"}}></div>
      {/*
        A <Switch> looks through all its children <Route>
        elements and renders the first one whose path
        matches the current URL. Use a <Switch> any time
        you have multiple routes, but you want only one
        of them to render at a time
      */}
      <Switch>
        <Route exact path="/" component={Dashboard} />
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
