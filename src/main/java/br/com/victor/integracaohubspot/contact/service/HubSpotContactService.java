package br.com.victor.integracaohubspot.contact.service;

import br.com.victor.integracaohubspot.contact.client.HubSpotContactFeignClient;
import br.com.victor.integracaohubspot.authorization.domain.HubSpotTokenService;
import org.springframework.stereotype.Service;

import java.util.Map;

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
        String accessToken = tokenService.getActiveAccessToken();
        return contactFeignClient.createContact("Bearer " + accessToken, formattedPayload);
    }
}