package br.com.victor.integracaohubspot.webhook.controller;

import br.com.victor.integracaohubspot.webhook.domain.EventDTO;
import br.com.victor.integracaohubspot.webhook.service.HubSpotWebhookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HubSpotWebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HubSpotWebhookService webhookService;

    @Test
    void testContactCreatedWebhookSuccess() throws Exception {
        Mockito.doNothing().when(webhookService).processEvents(Mockito.anyList());

        String requestBody = """
                [
                    {
                        "appId":123,
                        "eventId":456,
                        "subscriptionId":789,
                        "portalId":101112,
                        "occurredAt":161718,
                        "subscriptionType":"SUBSCRIPTION_TYPE",
                        "attemptNumber":1,
                        "objectId":192021,
                        "changeSource":"SOURCE",
                        "changeFlag":"CHANGE_FLAG"
                    }
                ]
                """;

        mockMvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Eventos processados com sucesso"));

        Mockito.verify(webhookService, Mockito.times(1)).processEvents(Mockito.anyList());
    }

    @Test
    void testContactCreatedWebhookFailure() throws Exception {
        Mockito.doThrow(new RuntimeException("Processing error")).when(webhookService).processEvents(Mockito.anyList());

        String requestBody = """
                [
                    {
                        "appId":123,
                        "eventId":456,
                        "subscriptionId":789,
                        "portalId":101112,
                        "occurredAt":161718,
                        "subscriptionType":"SUBSCRIPTION_TYPE",
                        "attemptNumber":1,
                        "objectId":192021,
                        "changeSource":"SOURCE",
                        "changeFlag":"CHANGE_FLAG"
                    }
                ]
                """;

        mockMvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro ao processar os eventos"));

        Mockito.verify(webhookService, Mockito.times(1)).processEvents(Mockito.anyList());
    }
}