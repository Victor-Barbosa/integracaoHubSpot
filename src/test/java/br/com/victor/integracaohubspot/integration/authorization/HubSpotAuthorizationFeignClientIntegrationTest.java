package br.com.victor.integracaohubspot.integration.authorization;

import br.com.victor.integracaohubspot.authorization.client.HubSpotAuthorizationFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class HubSpotAuthorizationFeignClientIntegrationTest {

    private HubSpotAuthorizationFeignClient hubSpotAuthorizationFeignClient;

    @BeforeEach
    void setup() {
        hubSpotAuthorizationFeignClient = mock(HubSpotAuthorizationFeignClient.class);
    }

    @Test
    void shouldRequestTokenSuccessfully() {
        when(hubSpotAuthorizationFeignClient.requestToken(
                eq("application/x-www-form-urlencoded"),
                eq("grant_type=client_credentials"))
        ).thenReturn(Map.of(
                "access_token", "mockedToken",
                "expires_in", 1800
        ));

        Map<String, Object> response = hubSpotAuthorizationFeignClient.requestToken(
                "application/x-www-form-urlencoded",
                "grant_type=client_credentials"
        );

        assertEquals("mockedToken", response.get("access_token"));
        assertEquals(1800, response.get("expires_in"));

        verify(hubSpotAuthorizationFeignClient, times(1))
                .requestToken("application/x-www-form-urlencoded", "grant_type=client_credentials");
    }
}