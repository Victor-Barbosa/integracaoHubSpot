package br.com.victor.integracaohubspot.authorization.service;

import br.com.victor.integracaohubspot.authorization.domain.OAuthProperties;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HubSpotAuthorizationServiceTest {

    @Autowired
    private HubSpotAuthorizationService hubSpotAuthorizationService;

    @Test
    void buildAuthorizationUrl_ShouldReturnCorrectUrl_WhenValidPropertiesAreProvided() {
        OAuthProperties oauthProperties = Mockito.mock(OAuthProperties.class);
        Mockito.when(oauthProperties.getAuthUrl()).thenReturn("https://auth.hubspot.com/oauth/authorize");
        Mockito.when(oauthProperties.getClientId()).thenReturn("test-client-id");
        Mockito.when(oauthProperties.getRedirectUri()).thenReturn("http://localhost:8080/callback");
        Mockito.when(oauthProperties.getScopes()).thenReturn("contacts");

        HubSpotAuthorizationService authorizationService = new HubSpotAuthorizationService(oauthProperties);

        String expectedUrl = "https://auth.hubspot.com/oauth/authorize"
                + "?client_id=test-client-id"
                + "&redirect_uri=" + URLEncoder.encode("http://localhost:8080/callback", StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode("contacts", StandardCharsets.UTF_8);

        String actualUrl = authorizationService.buildAuthorizationUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void buildAuthorizationUrl_ShouldEncodeScopesAndRedirectUriCorrectly() {
        OAuthProperties oauthProperties = Mockito.mock(OAuthProperties.class);
        Mockito.when(oauthProperties.getAuthUrl()).thenReturn("https://auth.hubspot.com/oauth/authorize");
        Mockito.when(oauthProperties.getClientId()).thenReturn("test-client-id");
        Mockito.when(oauthProperties.getRedirectUri()).thenReturn("http://localhost:8080/callback?param=value");
        Mockito.when(oauthProperties.getScopes()).thenReturn("contacts deals");

        HubSpotAuthorizationService authorizationService = new HubSpotAuthorizationService(oauthProperties);

        String expectedUrl = "https://auth.hubspot.com/oauth/authorize"
                + "?client_id=test-client-id"
                + "&redirect_uri=" + URLEncoder.encode("http://localhost:8080/callback?param=value",
                StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode("contacts deals", StandardCharsets.UTF_8);

        String actualUrl = authorizationService.buildAuthorizationUrl();

        assertEquals(expectedUrl, actualUrl);
    }
}