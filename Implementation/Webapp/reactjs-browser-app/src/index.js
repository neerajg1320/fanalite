import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './app/App';
import AppRouterSample from './app/samples/AppRouterSample';



ReactDOM.render(
  <React.StrictMode>
    {/* <App /> */}
    <AppRouterSample />
  </React.StrictMode>,
  document.getElementById('root')
);