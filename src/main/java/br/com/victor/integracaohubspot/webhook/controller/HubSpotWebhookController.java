package br.com.victor.integracaohubspot.webhook.controller;

import br.com.victor.integracaohubspot.webhook.domain.EventDTO;
import br.com.victor.integracaohubspot.webhook.service.HubSpotWebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/webhook")
@Slf4j
public class HubSpotWebhookController {
    private final HubSpotWebhookService webhookService;

    public HubSpotWebhookController(HubSpotWebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping
    public ResponseEntity<?> contactCreatedWebhook(
            @RequestBody List<EventDTO> events) {
        try {
            log.info("Eventos recebidos: {}", events);
            webhookService.processEvents(events);
            return ResponseEntity.ok("Eventos processados com sucesso");
        } catch (Exception e) {
            log.error("Erro encontrado ao processar eventos", e);
            return ResponseEntity.status(500).body("Erro ao processar os eventos");
        }
    }
}