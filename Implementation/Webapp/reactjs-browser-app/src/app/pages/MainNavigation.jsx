import React from 'react';
import { Link } from "react-router-dom";

import classes from './MainNavigation.module.css';

function MainNavigation() {
    return (
        <header className={classes.header}>
            <div className={classes.logo} >Insights</div>
            <nav>
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
            </nav>
        </header>
    )
}

export default MainNavigation;