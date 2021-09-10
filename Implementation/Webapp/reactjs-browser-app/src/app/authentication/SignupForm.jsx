import React from "react";
import { Link } from "react-router-dom";
import logoImg from "../../assets/Insights.png";
import {Card, Logo, Form, Input, Button} from './AuthForm';


function SignupForm() {
    return (
        <Card>
            <Logo src={logoImg}/>
            <Form>
                <Input type="email" placeholder="email" />
                <Input type="password" placeholder="password" />
                <Input type="password" placeholder="confirm password" />
                <Button>Sign Up</Button>
            </Form>
            <Link to="/signin">Already have an account?</Link>
        </Card>
    );
}

export default SignupForm;