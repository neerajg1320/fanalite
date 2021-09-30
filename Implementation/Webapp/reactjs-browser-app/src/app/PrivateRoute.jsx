import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { useAuth } from './authentication/AuthContext';

function PrivateRoute({ component: Component, ...rest }) {
  const { authTokens } = useAuth();
  
  return(
    <Route {...rest} render={(props) => (
      authTokens ? (
        <Component {...props} />
      ) : (
        <Redirect to="/signin" />
      )
    )}
    />
  );
}

export default PrivateRoute;