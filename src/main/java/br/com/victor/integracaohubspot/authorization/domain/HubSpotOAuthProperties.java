package br.com.victor.integracaohubspot.authorization.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HubSpotOAuthProperties implements OAuthProperties {

    @Value( "${hubspot.oauth.authUrl}")
    private String authUrl;

    @Value( "${hubspot.oauth.clientId}")
    private String clientId;

    @Value( "${hubspot.oauth.redirectUri}")
    private String redirectUri;

    @Value( "${hubspot.oauth.scopes}")
    private List<String> scopes;

    @Override
    public String getAuthUrl() {
        return authUrl;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }

    @Override
    public String getScopes() {
        return String.join("%20", scopes);
    }
}