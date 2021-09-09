import React from 'react';
import { A } from 'hookrouter';

export const Navbar = () => {
    return (
        <div>
            <h1>My Website</h1>
            <div style={{display:'flex', justifyContent: 'space-around', padding: '20px', borderBottom: '1px solid grey'}}>
                <A href="/">Home</A>
                <A href="/about">About</A>
                <A href="/contact/1234">Contact</A>
            </div>
        </div>
    )
}