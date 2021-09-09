import React, { useEffect, useState } from 'react';
import './App.css';
import { Route, Link, BrowserRouter as Router } from "react-router-dom";
import { Redirect } from 'react-router';

import Regex from './regex/Regex';
import Login from './authentication/Login';
import LoginUser from './authentication/LoginUser';
import Dashboard from './dashboard/Dashboard';
import Preferences from './preferences/Prferences';

import useSessionToken from './useSessionToken';
import useStorageToken from './useStorageToken';
import { Wrapper } from './components/Wrapper';
import { Navbar } from './components/Navbar';

import { Home } from './pages/Home';
import { About } from './pages/About'
import { Contact } from './pages/Contact'
import { NotFound } from './pages/NotFound'

const routes = {
  '/': () => <Home/>,
  '/about*': () => <About/>,
  '/contact/:name': ({name}) => <Contact name={name}/>,
}

function App() {
  return (
    <div>
      <Regex/>
    </div>
  )  
}

export default App;
