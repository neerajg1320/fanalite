import React, {useState} from "react";
import { Link, Redirect } from "react-router-dom";
import logoImg from "../../assets/Insights.png";
import {Card, Logo, Form, Input, Button, Error} from './AuthForm';
import { useAuth } from "./AuthContext";
import axios from 'axios';

function SigninForm() {
    const [isLoggedIn, setLoggedIn] = useState(false);
    const [isError, setIsError] = useState(false);
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const { setAuthTokens } = useAuth();

    function postLogin() {
        axios.post("http://localhost:8080/login", {
            userName, 
            password
        }).then(result => {
            console.log("result.status:", result.status)
            if (result.status === 200) {
                setAuthTokens(result.data);
                setLoggedIn(true);
            } else {
                setIsError(true);
            }
        }).catch(e => {
            console.log("exception:", e.message)
            setIsError(true);
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
                <Button onClick={postLogin}>Sign In</Button>
            </Form>
            <Link to="/signup">Don't have an account?</Link>
            { isError && <Error>The username or password is incorrect</Error>}
        </Card>
    );
}

export default SigninForm;