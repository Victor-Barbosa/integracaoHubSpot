package br.com.victor.integracaohubspot.authorization.controller;

import br.com.victor.integracaohubspot.authorization.domain.HubSpotTokenService;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HubSpotCallbackController {

    private final HubSpotTokenService tokenService;

    public HubSpotCallbackController(HubSpotTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/auth-callback")
    public ResponseEntity<String> handleOAuthCallback(
            @RequestParam("code") @NotBlank String code) {
        try {
            tokenService.getAccessToken(code);
            return ResponseEntity.ok("Autorização concluída");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Código de autorização inválido");
        } catch (Exception ex) {
            log.error("Erro ao processar o callback: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).body("Erro interno ao processar o callback");
        }
    }
}