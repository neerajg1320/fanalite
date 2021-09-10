import { Link } from "react-router-dom";

function MainNavigation() {
    return (
        <header>
            <div>Insights</div>
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