package br.com.victor.integracaohubspot.webhook.service;

import br.com.victor.integracaohubspot.webhook.domain.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HubSpotWebhookService {

    public void processEvents(List<EventDTO> events) {
        for (EventDTO event : events) {
            processEvent(event);
        }
    }

    private void processEvent(EventDTO event) {
        String subscriptionType = event.getSubscriptionType();

        if (subscriptionType == null) {
            log.warn("Evento recebido sem subscriptionType. Detalhes: {}", event);
            return;
        }

        switch (subscriptionType.toLowerCase()) {
            case "contact.creation":
                processContactCreation(event);
                break;

            default:
                log.warn("Tipo de assinatura não reconhecido: {}. Evento: {}", subscriptionType, event);
        }
    }

    private void processContactCreation(EventDTO event) {
        Long objectId = event.getObjectId();
        if (objectId == null || objectId <= 0) {
            log.warn("Criação de contato ignorada. ID do objeto inválido: {}", objectId);
            return;
        }

        log.info("Processando criação de contato - ID: {}", objectId);
    }
}