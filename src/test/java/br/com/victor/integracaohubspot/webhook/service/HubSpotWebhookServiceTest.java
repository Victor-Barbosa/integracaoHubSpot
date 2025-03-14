package br.com.victor.integracaohubspot.webhook.service;

import br.com.victor.integracaohubspot.webhook.domain.EventDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.mockito.Mockito.*;

@SpringBootTest
public class HubSpotWebhookServiceTest {

    @Test
    void testProcessEvents_withValidContactCreationEvent() {
        HubSpotWebhookService hubSpotWebhookService = spy(HubSpotWebhookService.class);

        EventDTO event = new EventDTO(
                1L, 1L, 1L, 1L, System.currentTimeMillis(),
                "contact.creation", 1, 101L, "API", null
        );

        assertThatNoException().isThrownBy(() -> hubSpotWebhookService.processEvents(List.of(event)));

        verify(hubSpotWebhookService, times(1)).processEvents(anyList());
    }

    @Test
    void testProcessEvents_withNullSubscriptionType() {
        HubSpotWebhookService hubSpotWebhookService = spy(HubSpotWebhookService.class);

        EventDTO event = new EventDTO(
                1L, 1L, 1L, 1L, System.currentTimeMillis(),
                null, 1, 101L, "API", null
        );

        assertThatNoException().isThrownBy(() -> hubSpotWebhookService.processEvents(List.of(event)));

        verify(hubSpotWebhookService, times(1)).processEvents(anyList());
    }

    @Test
    void testProcessEvents_withUnknownSubscriptionType() {
        HubSpotWebhookService hubSpotWebhookService = spy(HubSpotWebhookService.class);

        EventDTO event = new EventDTO(
                1L, 1L, 1L, 1L, System.currentTimeMillis(),
                "unknown.type", 1, 101L, "API", null
        );

        assertThatNoException().isThrownBy(() -> hubSpotWebhookService.processEvents(List.of(event)));

        verify(hubSpotWebhookService, times(1)).processEvents(anyList());
    }

    @Test
    void testProcessEvents_withInvalidContactCreationObjectId() {
        HubSpotWebhookService hubSpotWebhookService = spy(HubSpotWebhookService.class);

        EventDTO event = new EventDTO(
                1L, 1L, 1L, 1L, System.currentTimeMillis(),
                "contact.creation", 1, null, "API", null
        );

        assertThatNoException().isThrownBy(() -> hubSpotWebhookService.processEvents(List.of(event)));

        verify(hubSpotWebhookService, times(1)).processEvents(anyList());
    }

    @Test
    void testProcessEvents_withEmptyEventList() {
        HubSpotWebhookService hubSpotWebhookService = spy(HubSpotWebhookService.class);

        assertThatNoException().isThrownBy(() -> hubSpotWebhookService.processEvents(Collections.emptyList()));

        verify(hubSpotWebhookService, times(1)).processEvents(Collections.emptyList());
    }
}