package br.com.victor.integracaohubspot.authorization.controller;

import br.com.victor.integracaohubspot.authorization.service.HubSpotAuthorizationService;
import org.springframework.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/authorization")
@Slf4j
public class HubSpotAuthorizationController {

    private final HubSpotAuthorizationService authorizationService;

    public HubSpotAuthorizationController(HubSpotAuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<Void> buildAuthorizationUrl() {
        try {
            String url = authorizationService.buildAuthorizationUrl();
            log.info("URL de autorização criada com sucesso");

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(url));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (Exception e) {
            log.error("Erro ao criar a URL de autorização: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}