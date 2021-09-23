import { Card, Button } from '../authentication/AuthForm';
import React from 'react';
import Regex from '../regex/Regex';
import { useAuth } from '../authentication/AuthContext';

import config from '../config/default';

export default function Dashboard() {
    const { setAuthTokens } = useAuth();

    function signOut() {
        setAuthTokens();    
    }

    return (
        <div>
            {config.authentication.active &&
                <Card>
                    <Button onClick={signOut}>Sign Out</Button>
                </Card>
            }
            <div style={{margin:"40px"}}></div>
            <Regex/>
        </div>
    )
}