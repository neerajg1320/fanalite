import { Card, Button } from '../authentication/AuthForm';
import React from 'react';
import Regex from '../regex/Regex';
import { useAuth } from '../authentication/AuthContext';

export default function Dashboard() {
    const { setAuthTokens } = useAuth();

    function signOut() {
        setAuthTokens();    
    }

    return (
        <div>
            <Card>
                <Button onClick={signOut}>Sign Out</Button>
            </Card>
            <Regex/>
        </div>
    )
}