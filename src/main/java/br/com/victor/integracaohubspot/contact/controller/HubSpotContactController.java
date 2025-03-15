package br.com.victor.integracaohubspot.contact.controller;

import br.com.victor.integracaohubspot.contact.domain.ContactDTO;
import br.com.victor.integracaohubspot.contact.service.HubSpotContactService;
import io.github.bucket4j.Bucket;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/contacts")
@Slf4j
public class HubSpotContactController {

    private final HubSpotContactService contactService;
    private final Bucket bucket;

    public HubSpotContactController(HubSpotContactService contactService,
                                    Bucket bucket) {
        this.contactService = contactService;
        this.bucket = bucket;
    }

    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody @Valid ContactDTO contactDTO) {
        try {
            log.info("Criando contato...");
            if (!bucket.tryConsume(1)) {
                return ResponseEntity.status(429).body(
                        "Limite de requisições atingido. Por favor, aguarde um momento e tente novamente.");
            }

            Map<String, Object> response = contactService.createContact(contactDTO.toMap());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao criar contato: {}", e.getMessage());
            return ResponseEntity.status(500).body(
                    "Erro ao enviar dados para o HubSpot: " + e.getMessage());
        }
    }
}