package br.com.victor.integracaohubspot.contact.service;

import br.com.victor.integracaohubspot.contact.client.HubSpotContactFeignClient;
import br.com.victor.integracaohubspot.authorization.domain.HubSpotTokenService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Service
public class HubSpotContactService {

    private final HubSpotContactFeignClient contactFeignClient;
    private final HubSpotTokenService tokenService;

    public HubSpotContactService(HubSpotContactFeignClient contactFeignClient, HubSpotTokenService tokenService) {
        this.contactFeignClient = contactFeignClient;
        this.tokenService = tokenService;
    }

    public Map<String, Object> createContact(Map<String, Object> contactDTO) {
        Map<String, Object> formattedPayload = Map.of(
                "properties", contactDTO);
        log.info("Obtendo o token de acesso ativo do serviço HubSpotTokenService.");
        String accessToken = tokenService.getActiveAccessToken();
        log.info("Enviando solicitação para criar um contato com os dados: {}", formattedPayload);
        return contactFeignClient.createContact("Bearer " + accessToken, formattedPayload);
    }
}