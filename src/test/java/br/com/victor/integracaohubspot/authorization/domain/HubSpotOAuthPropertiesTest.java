package br.com.victor.integracaohubspot.authorization.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {
        "hubspot.oauth.authUrl=https://example.com/oauth",
        "hubspot.oauth.clientId=test-client-id",
        "hubspot.oauth.redirectUri=https://example.com/callback",
        "hubspot.oauth.scopes=scope1,scope2,scope3"
})
public class HubSpotOAuthPropertiesTest {

    @Autowired
    private HubSpotOAuthProperties hubSpotOAuthProperties;

    private void setPrivateField(Object targetObject, String fieldName, Object value) throws Exception {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    public void shouldHandleEmptyScopesWithReflection() throws Exception {
        HubSpotOAuthProperties localHubSpotOAuthProperties = new HubSpotOAuthProperties();
        List<String> emptyScopes = List.of();

        setPrivateField(localHubSpotOAuthProperties, "scopes", emptyScopes);

        String scopesResult = localHubSpotOAuthProperties.getScopes();

        assertEquals("", scopesResult);
    }

    @Test
    public void shouldHandleSingleScopeWithReflection() throws Exception {
        HubSpotOAuthProperties localHubSpotOAuthProperties = new HubSpotOAuthProperties();
        List<String> singleScope = List.of("single-scope");

        setPrivateField(localHubSpotOAuthProperties, "scopes", singleScope);

        String scopesResult = localHubSpotOAuthProperties.getScopes();

        assertEquals("single-scope", scopesResult);
    }

    @Test
    public void shouldReturnCorrectAuthUrl() {
        String expectedAuthUrl = "https://example.com/oauth";

        assertEquals(expectedAuthUrl, hubSpotOAuthProperties.getAuthUrl());
    }

    @Test
    public void shouldHandleEmptyAuthUrl() throws Exception {
        HubSpotOAuthProperties localHubSpotOAuthProperties = new HubSpotOAuthProperties();

        setPrivateField(localHubSpotOAuthProperties, "authUrl", "");

        String authUrlResult = localHubSpotOAuthProperties.getAuthUrl();

        assertEquals("", authUrlResult);
    }
}