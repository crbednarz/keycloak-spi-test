package com.example.demo.protocol;

import java.util.ArrayList;
import java.util.List;

import org.keycloak.models.ClientSessionContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.oidc.mappers.AbstractOIDCProtocolMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAccessTokenMapper;
import org.keycloak.protocol.oidc.mappers.OIDCAttributeMapperHelper;
import org.keycloak.protocol.oidc.mappers.OIDCIDTokenMapper;
import org.keycloak.protocol.oidc.mappers.UserInfoTokenMapper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.AccessToken;

public class DemoProtocolMapper extends AbstractOIDCProtocolMapper implements OIDCAccessTokenMapper, OIDCIDTokenMapper, UserInfoTokenMapper {

    public static final String ID = "demo-protocol-mapper";

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    static {
        OIDCAttributeMapperHelper.addTokenClaimNameConfig(configProperties);
        OIDCAttributeMapperHelper.addIncludeInTokensConfig(configProperties, DemoProtocolMapper.class);
    }

    @Override
    public String getDisplayCategory() {
        return TOKEN_MAPPER_CATEGORY;
    }

    @Override
    public String getDisplayType() {
        return "DemoProtocolMapper";
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getHelpText() {
        return "";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    private String doAdditionalBackendCall() {
        return "EXAMPLE ADDITIONAL BACKEND RESULT";
    }

    @Override
    public AccessToken transformAccessToken(AccessToken token, ProtocolMapperModel mappingModel, KeycloakSession keycloakSession, UserSessionModel userSession, ClientSessionContext clientSessionCtx) {
        // Retrieve data from the UserStorageProvider's backend call.
        String backendCallResult = userSession.getNote("demo-backend-data-note");
        token.getOtherClaims().put("demo-backend-data-1", backendCallResult);

        // Do another mock call to the backend. In order to keep things simple,
        // the result here is a String as well.
        String additionalResult = doAdditionalBackendCall();
        token.getOtherClaims().put("demo-backend-data-2", additionalResult);

        setClaim(token, mappingModel, userSession, keycloakSession, clientSessionCtx);
        return token;
    }
}
