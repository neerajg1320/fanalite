import React from 'react';
import { Link } from "react-router-dom";

import classes from './MainNavigation.module.css';

import config from '../config/default';


function MainNavigation() {
    let links;

    if (config.authentication.active) {
        links = <ul>
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
        </ul>;
    } else {
        links = <ul>
            <li>
                <Link to="/dashboard">Dashboard</Link>
            </li>
        </ul>;
    }
    return (
        <header className={classes.header}>
            <div className={classes.logo} >Insights</div>
            <nav>
                {links}
            </nav>
        </header>
    )
}

export default MainNavigation;