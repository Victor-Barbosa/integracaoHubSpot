package br.com.victor.integracaohubspot.authorization.controller;

import br.com.victor.integracaohubspot.authorization.domain.HubSpotTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

class HubSpotCallbackControllerTest {

    private HubSpotTokenService tokenService;
    private HubSpotCallbackController callbackController;

    @BeforeEach
    void setUp() {
        tokenService = Mockito.mock(HubSpotTokenService.class);
        callbackController = new HubSpotCallbackController(tokenService);
    }

    @Test
    void shouldReturnOkWhenAuthorizationCodeIsValid() {
        String validCode = "validCode";

        ResponseEntity<String> response = callbackController.handleOAuthCallback(validCode);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Autorização concluída", response.getBody());

        verify(tokenService).getAccessToken(validCode);
    }

    @Test
    void shouldReturnBadRequestWhenCodeIsInvalid() {
        String invalidCode = "invalidCode";
        doThrow(new IllegalArgumentException("Código inválido")).when(tokenService).getAccessToken(invalidCode);

        ResponseEntity<String> response = callbackController.handleOAuthCallback(invalidCode);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Código de autorização inválido", response.getBody());

        verify(tokenService).getAccessToken(invalidCode);
    }

    @Test
    void shouldReturnInternalServerErrorOnUnexpectedException() {
        String code = "unexpectedError";
        doThrow(new RuntimeException("Erro interno")).when(tokenService).getAccessToken(code);

        ResponseEntity<String> response = callbackController.handleOAuthCallback(code);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro interno ao processar o callback", response.getBody());
    }
}