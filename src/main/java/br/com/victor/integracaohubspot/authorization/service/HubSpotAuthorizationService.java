package br.com.victor.integracaohubspot.authorization.service;

import br.com.victor.integracaohubspot.authorization.domain.OAuthProperties;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class HubSpotAuthorizationService {

    private final OAuthProperties oauthProperties;

    public HubSpotAuthorizationService(OAuthProperties oauthProperties) {
        this.oauthProperties = oauthProperties;
    }

    public String buildAuthorizationUrl() {
        String scopesEncoded = URLEncoder.encode(oauthProperties.getScopes(),
                StandardCharsets.UTF_8);
        return oauthProperties.getAuthUrl()
                + "?client_id=" + oauthProperties.getClientId()
                + "&redirect_uri=" + URLEncoder.encode(oauthProperties.getRedirectUri(),
                StandardCharsets.UTF_8)
                + "&scope=" + scopesEncoded;
    }
}