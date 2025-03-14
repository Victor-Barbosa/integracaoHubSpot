package br.com.victor.integracaohubspot.authorization.controller;

import br.com.victor.integracaohubspot.authorization.service.HubSpotAuthorizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HubSpotAuthorizationControllerTest {

    @Mock
    private HubSpotAuthorizationService authorizationService;

    @InjectMocks
    private HubSpotAuthorizationController controller;

    @Test
    void shouldReturnFoundWithLocationHeaderWhenServiceReturnsUrl() throws Exception {
        String mockUrl = "https://teste.com";
        when(authorizationService.buildAuthorizationUrl()).thenReturn(mockUrl);

        ResponseEntity<Void> response = controller.buildAuthorizationUrl();

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals(URI.create(mockUrl), response.getHeaders().getLocation());
        verify(authorizationService, times(1)).buildAuthorizationUrl();
    }

    @Test
    void shouldReturnInternalServerErrorWhenServiceThrowsException() {
        when(authorizationService.buildAuthorizationUrl()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Void> response = controller.buildAuthorizationUrl();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(authorizationService, times(1)).buildAuthorizationUrl();
    }
}