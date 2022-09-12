package com.example.demo.storage;

import java.util.Collections;
import java.util.Set;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

public class DemoUserStorageProvider
        implements UserStorageProvider, UserLookupProvider, CredentialInputValidator, CredentialInputUpdater {

    private KeycloakSession session;
    private ComponentModel model;
    private UserModel user;

    public DemoUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;

    }

    @Override
    public void close() {

    }

    @Override
    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {

    }

    @Override
    public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {
        return Collections.<String>emptySortedSet();
    }

    @Override
    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
        return false;
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return credentialType.equals(CredentialModel.PASSWORD);
    }

    private String doBackendServiceCall() {
        return "EXAMPLE BACKEND RESULT";
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
        // In order to simplify this example, doBackendServiceCall simply returns a
        // string which we'd like to act on later in the ProtocolMapper.
        String backendCallResult = doBackendServiceCall();
        session.setAttribute("demo-backend-data-attribute", backendCallResult);

        // Authenticate for a single user "user" with password "password"
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel))
            return false;
        var password = ((UserCredentialModel) input).getValue();
        return user.getUsername() == "user" && password.equals("password");
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return credentialType.equals(CredentialModel.PASSWORD);
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        return null;
    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        return getUserByUsername(id, realm);
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        if (this.user == null) {
            this.user = new AbstractUserAdapter(session, realm, model) {
                @Override
                public String getUsername() {
                    return "user";
                }
            };
        }

        return this.user;
    }
}
