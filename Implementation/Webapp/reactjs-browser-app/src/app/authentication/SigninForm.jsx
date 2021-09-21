import React, {useEffect, useState} from "react";
import { Link, Redirect } from "react-router-dom";
import logoImg from "../../assets/Insights.png";
import {Card, Logo, Form, Input, Button, Error} from './AuthForm';
import { useAuth } from "./AuthContext";
import axios from 'axios';

import { firebaseAuth } from '../../firebaseConfig';

import config from '../config/default.json';

function SigninForm() {
    const [isLoggedIn, setLoggedIn] = useState(false);
    const [isError, setIsError] = useState(false);
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const { authTokens, setAuthTokens } = useAuth();

    // useEffect is watching authTokens for value change
    // This is helpful in case when we directly enter to the 
    // signin page in the browser. The SigninForm page's
    // useEffect function is called before App's useEffect
    // function. Hence we monitor for the setting of authTokens
    // caused by App's useEffect function
    useEffect(() => {
        console.log("SigninForm: useEffect(): authTokens:", authTokens)
        if(authTokens) {
            setLoggedIn(true);
        }
    }, [authTokens]);

    function postNodeStubLogin() {
        axios.post(config.stub_server, {
            userName,
            password
        }).then(result => {
            console.log("result.status:", result.status);
            console.log("result.data:", result.data);

            if (result.status === 200) {
                setAuthTokens(result.data)
                setLoggedIn(true);
            } else {
                setIsError(true);
            }
        }).catch(e => {
            console.log("exception:", e.message)
            setIsError(true);
        })
    }

    function postNodeServerLogin() {
        axios.post(config.node_server, {
            strategy: "local",
            email: userName,
            password
        }).then(result => {
            console.log("result.status:", result.status);
            console.log("result.data:", result.data);

            if (result.status === 201) {
                setAuthTokens(result.data)
                setLoggedIn(true);
            } else {
                setIsError(true);
            }
        }).catch(e => {
            console.log("exception:", e.message)
            setIsError(true);
        })
    }

    function postFirebaseLogin() {
        firebaseAuth.signInWithEmailAndPassword(userName, password)
        .then((user) => {
            console.log("Firebase: user: ", user);
            setAuthTokens({token: "token123"});
            setLoggedIn(true);
        })
        .catch((e) => {
            console.log("Firebase: exception: ", e.message);
        })        
    }

    if (isLoggedIn) {
        return <Redirect to="/dashboard" />;
    }

    return (
        <Card>
            <Logo src={logoImg}/>
            <Form>
                <Input type="email" placeholder="email" onChange={e => setUserName(e.target.value)} />
                <Input type="password" placeholder="password" onChange={e => setPassword(e.target.value)} />
                {/* <Button onClick={postLogin}>Sign In</Button> */}
                <Button onClick={postNodeServerLogin}>Sign In</Button>
            </Form>
            <Link to="/signup">Don't have an account?</Link>
            { isError && <Error>The username or password is incorrect</Error>}
        </Card>
    );
}

export default SigninForm;