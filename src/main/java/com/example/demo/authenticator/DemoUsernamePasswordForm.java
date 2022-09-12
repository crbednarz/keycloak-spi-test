package com.example.demo.authenticator;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;

public class DemoUsernamePasswordForm extends UsernamePasswordForm {


    @Override
    public void action(AuthenticationFlowContext context) {
        super.action(context);

        // Convert keycloak session attribute to a user session note, so it will be accessible
        // from the ProtocolMapper later.
        var backendCallResult = (String)context.getSession().getAttribute("demo-backend-data-attribute");
        context.getAuthenticationSession().setUserSessionNote("demo-backend-data-note", backendCallResult);
    }
}
