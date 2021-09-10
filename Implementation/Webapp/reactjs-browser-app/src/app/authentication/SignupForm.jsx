import React, { useState } from "react";
import { Link, Redirect } from "react-router-dom";
import logoImg from "../../assets/Insights.png";
import {Card, Logo, Form, Input, Button} from './AuthForm';

import {firebaseAuth} from '../../firebaseConfig';

function SignupForm() {
    const [isRegesitered, setRegistered] = useState(false)
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    function postFirebaseSignup() {
        if (password && password === confirmPassword) {
            firebaseAuth.createUserWithEmailAndPassword(userName, password)
            .then((user) => {
                console.log("Firebase: user: ", user);
                setRegistered(true);
            })
            .catch((e) => {
                console.log("Firebase: exception: ", e.message);
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
                <Button onClick={postFirebaseSignup}>Sign Up</Button>
            </Form>
            <Link to="/signin">Already have an account?</Link>
        </Card>
    );
}

export default SignupForm;