import React from "react";
import { Link } from "react-router-dom";
import logoImg from "../../assets/Insights.png";
import {Card, Logo, Form, Input, Button} from './AuthForm';


function SigninForm() {
    
    return (
        <Card>
            <Logo src={logoImg}/>
            <Form>
                <Input type="email" placeholder="email" />
                <Input type="password" placeholder="password" />
                <Button>Sign In</Button>
            </Form>
            <Link to="/signup">Don't have an account?</Link>
        </Card>
    );
}

export default SigninForm;