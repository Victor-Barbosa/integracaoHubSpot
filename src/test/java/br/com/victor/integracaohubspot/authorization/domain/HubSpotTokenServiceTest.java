package br.com.victor.integracaohubspot.authorization.domain;

import br.com.victor.integracaohubspot.authorization.client.HubSpotAuthorizationFeignClient;
import br.com.victor.integracaohubspot.config.HubSpotConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class HubSpotTokenServiceTest {

    @Test
    void testGetAccessTokenSuccess() {
        HubSpotAuthorizationFeignClient feignClientMock = Mockito.mock(HubSpotAuthorizationFeignClient.class);
        HubSpotConfig configMock = Mockito.mock(HubSpotConfig.class);

        Mockito.when(configMock.getClientId()).thenReturn("testClientId");
        Mockito.when(configMock.getClientSecret()).thenReturn("testClientSecret");
        Mockito.when(configMock.getRedirectUri()).thenReturn("http://localhost/callback");

        Map<String, Object> mockResponse = Map.of(
                "access_token", "mockAccessToken",
                "refresh_token", "mockRefreshToken",
                "expires_in", 3600
        );

        Mockito.when(feignClientMock.requestToken(
                Mockito.eq("application/x-www-form-urlencoded"),
                Mockito.anyString()
        )).thenReturn(mockResponse);

        HubSpotTokenService service = new HubSpotTokenService(feignClientMock, configMock);

        AuthToken token = service.getAccessToken("testCode");

        Assertions.assertNotNull(token);
        Assertions.assertEquals("mockAccessToken", token.getAccessToken());
    }

    @Test
    void testGetAccessTokenThrowsExceptionOnError() {
        HubSpotAuthorizationFeignClient feignClientMock = Mockito.mock(HubSpotAuthorizationFeignClient.class);
        HubSpotConfig configMock = Mockito.mock(HubSpotConfig.class);

        Mockito.when(configMock.getClientId()).thenReturn("testClientId");
        Mockito.when(configMock.getClientSecret()).thenReturn("testClientSecret");
        Mockito.when(configMock.getRedirectUri()).thenReturn("http://localhost/callback");

        Mockito.when(feignClientMock.requestToken(
                Mockito.eq("application/x-www-form-urlencoded"),
                Mockito.anyString()
        )).thenThrow(new RuntimeException("Feign client error"));

        HubSpotTokenService service = new HubSpotTokenService(feignClientMock, configMock);

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            service.getAccessToken("testCode");
        });

        Assertions.assertEquals("Feign client error", exception.getMessage());
    }
}