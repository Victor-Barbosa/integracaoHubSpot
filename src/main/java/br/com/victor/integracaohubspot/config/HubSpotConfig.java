package br.com.victor.integracaohubspot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HubSpotConfig {

    @Value("${hubspot.oauth.clientId}")
    private String clientId;

    @Value("${hubspot.oauth.clientSecret}")
    private String clientSecret;

    @Value("${hubspot.oauth.redirectUri}")
    private String redirectUri;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}