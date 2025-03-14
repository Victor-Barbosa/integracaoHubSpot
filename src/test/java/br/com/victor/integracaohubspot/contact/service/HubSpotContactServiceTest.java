package br.com.victor.integracaohubspot.contact.service;

import br.com.victor.integracaohubspot.authorization.domain.HubSpotTokenService;
import br.com.victor.integracaohubspot.contact.client.HubSpotContactFeignClient;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class HubSpotContactServiceTest {

    @Mock
    private HubSpotContactFeignClient mockContactFeignClient;

    @Mock
    private HubSpotTokenService mockTokenService;

    @InjectMocks
    private HubSpotContactService hubSpotContactService;

    HubSpotContactServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateContactSuccessfully() {
        Map<String, Object> contactDTO = Map.of("email", "test@email.com", "firstname",
                "John", "lastname", "Doe");
        Map<String, Object> formattedPayload = Map.of("properties", contactDTO);
        Map<String, Object> response = Map.of("id", "12345", "status", "created");
        String accessToken = "dummyAccessToken";

        when(mockTokenService.getActiveAccessToken()).thenReturn(accessToken);
        when(mockContactFeignClient.createContact(eq("Bearer " + accessToken), eq(formattedPayload)))
                .thenReturn(response);

        Map<String, Object> result = hubSpotContactService.createContact(contactDTO);
        assertEquals(response, result);
    }

    @Test
    void shouldHandleErrorResponse() {
        Map<String, Object> contactDTO = Map.of("email", "test@email.com", "firstname",
                "John", "lastname", "Doe");
        Map<String, Object> formattedPayload = Map.of("properties", contactDTO);
        String accessToken = "dummyAccessToken";

        when(mockTokenService.getActiveAccessToken()).thenReturn(accessToken);
        when(mockContactFeignClient.createContact(eq("Bearer " + accessToken), eq(formattedPayload)))
                .thenThrow(new RuntimeException("Failed to create contact"));

        try {
            hubSpotContactService.createContact(contactDTO);
        } catch (RuntimeException e) {
            assertEquals("Failed to create contact", e.getMessage());
        }
    }
}