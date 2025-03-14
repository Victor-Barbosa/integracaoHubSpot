package br.com.victor.integracaohubspot.authorization.controller;

import br.com.victor.integracaohubspot.authorization.service.HubSpotAuthorizationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/authorization")
public class HubSpotAuthorizationController {

    private final HubSpotAuthorizationService authorizationService;

    public HubSpotAuthorizationController(HubSpotAuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<Void> buildAuthorizationUrl() {
        try {
            String url = authorizationService.buildAuthorizationUrl();

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(url));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}