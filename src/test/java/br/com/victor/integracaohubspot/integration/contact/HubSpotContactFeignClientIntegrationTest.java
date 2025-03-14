package br.com.victor.integracaohubspot.integration.contact;

import br.com.victor.integracaohubspot.contact.client.HubSpotContactFeignClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class HubSpotContactFeignClientIntegrationTest {

    @Mock
    private HubSpotContactFeignClient hubSpotContactFeignClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateContactSuccessfully() {
        String authorizationToken = "Bearer test-token";
        Map<String, Object> contactProperties = Map.of(
                "email", "test@example.com",
                "firstName", "John",
                "lastName", "Doe"
        );
        Map<String, Object> mockResponse = Map.of("status", "success");

        when(hubSpotContactFeignClient.createContact(authorizationToken, contactProperties))
                .thenReturn(mockResponse);

        Map<String, Object> response = hubSpotContactFeignClient.createContact(authorizationToken, contactProperties);

        assertNotNull(response, "A resposta não pode ser nula");
        assertEquals("success", response.get("status"), "O status da resposta deve ser 'success'");

        verify(hubSpotContactFeignClient, times(1)).createContact(authorizationToken, contactProperties);
    }

    @Test
    void shouldHandleErrorResponse() {
        String authorizationToken = "Bearer invalid-token";
        Map<String, Object> contactProperties = Map.of(
                "email", "test@example.com",
                "firstName", "John",
                "lastName", "Doe"
        );

        when(hubSpotContactFeignClient.createContact(authorizationToken, contactProperties))
                .thenThrow(new RuntimeException("Unauthorized"));

        RuntimeException exception = null;
        try {
            hubSpotContactFeignClient.createContact(authorizationToken, contactProperties);
        } catch (RuntimeException e) {
            exception = e;
        }

        assertNotNull(exception, "A exceção deve ser lançada");
        assertEquals("Unauthorized", exception.getMessage(), "A mensagem de erro deve ser 'Unauthorized'");

        verify(hubSpotContactFeignClient, times(1)).createContact(authorizationToken, contactProperties);
    }
}
