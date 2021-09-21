import React, { useState } from "react";
import { Link, Redirect } from "react-router-dom";
import logoImg from "../../assets/Insights.png";
import {Card, Logo, Form, Input, Button, Error} from './AuthForm';

import {firebaseAuth} from '../../firebaseConfig';
import axios from "axios";
import config from "../config/default";

function SignupForm() {
    const [isRegesitered, setRegistered] = useState(false);
    const [isError, setIsError] = useState(false);
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    function postNodeServerSignup() {
        if (password && password === confirmPassword) {
            axios.post(config.server.register, {
                email: userName,
                password
            }).then(result => {
                console.log("result.status:", result.status);
                console.log("result.data:", result.data);

                if (result.status === 201) {
                    setRegistered(true);
                } else {
                    setIsError(true);
                }
            }).catch(e => {
                console.log("exception:", e.message)
                setIsError(true);
            })
        }
    }

    function postFirebaseSignup() {
        if (password && password === confirmPassword) {
            firebaseAuth.createUserWithEmailAndPassword(userName, password)
            .then((user) => {
                console.log("Firebase: user: ", user);
                setRegistered(true);
            })
            .catch((e) => {
                console.log("Firebase: exception: ", e.message);
                setIsError(true);
            })       
        } 
    }

    if (isRegesitered) {
        return <Redirect to="/signin" />
    }

    return (
        <Card>
            <Logo src={logoImg}/>
            <Form>
                <Input type="email" placeholder="email" onChange={e => setUserName(e.target.value)} />
                <Input type="password" placeholder="password" onChange={e => setPassword(e.target.value)} />
                <Input type="password" placeholder="confirm password" onChange={e => setConfirmPassword(e.target.value)} />
                <Button onClick={postNodeServerSignup}>Sign Up</Button>
            </Form>
            <Link to="/signin">Already have an account?</Link>
            { isError && <Error>The username or password is incorrect</Error>}
        </Card>
    );
}

export default SignupForm;