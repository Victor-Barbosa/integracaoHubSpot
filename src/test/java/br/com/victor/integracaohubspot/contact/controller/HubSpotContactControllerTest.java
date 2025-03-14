package br.com.victor.integracaohubspot.contact.controller;

import br.com.victor.integracaohubspot.contact.service.HubSpotContactService;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HubSpotContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private HubSpotContactService contactService;

    private Bucket bucket;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public HubSpotContactService contactService() {
            return Mockito.mock(HubSpotContactService.class);
        }

        @Bean
        public Bucket bucket() {
            return Mockito.mock(Bucket.class);
        }
    }

    @Autowired
    public void setMocks(HubSpotContactService contactService, Bucket bucket) {
        this.contactService = contactService;
        this.bucket = bucket;
    }

    @Test
    void whenValidRequest_thenReturn200WithResponse() throws Exception {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);

        Map<String, Object> mockedResponse = new HashMap<>();
        mockedResponse.put("status", "success");
        Mockito.when(contactService.createContact(any(Map.class))).thenReturn(mockedResponse);

        String requestBody = """
                    {
                      "firstName": "John",
                      "lastName": "Doe",
                      "email": "john.doe@example.com"
                    }
                """;

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));
    }

    @Test
    void whenRateLimitReached_thenReturn429() throws Exception {
        Mockito.when(bucket.tryConsume(1)).thenReturn(false);

        String requestBody = """
                    {
                      "firstName": "John",
                      "lastName": "Doe",
                      "email": "john.doe@example.com"
                    }
                """;

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isTooManyRequests())
                .andExpect(content().string("Limite de requisições atingido. Por favor, aguarde um momento e tente novamente."));
    }

    @Test
    void whenServiceThrowsException_thenReturn500() throws Exception {
        Mockito.when(bucket.tryConsume(1)).thenReturn(true);
        Mockito.when(contactService.createContact(any(Map.class))).thenThrow(new RuntimeException("HubSpot API error"));

        String requestBody = """
                    {
                      "firstName": "John",
                      "lastName": "Doe",
                      "email": "john.doe@example.com"
                    }
                """;

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro ao enviar dados para o HubSpot: HubSpot API error"));
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        String requestBody = """
                    {
                      "firstName": "John",
                      "lastName": "Doe",
                      "email": "invalid-email"
                    }
                """;

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsEmpty() throws Exception {
        String requestBody = """
                    {
                      "firstName": "John",
                      "lastName": "Doe",
                      "email": ""
                    }
                """;

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}