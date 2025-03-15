package br.com.victor.integracaohubspot.authorization.domain;

import br.com.victor.integracaohubspot.authorization.client.HubSpotAuthorizationFeignClient;
import br.com.victor.integracaohubspot.config.HubSpotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class HubSpotTokenService {

    private final HubSpotAuthorizationFeignClient feignClient;
    private final HubSpotConfig config;

    private AuthToken currentToken;

    public HubSpotTokenService(HubSpotAuthorizationFeignClient feignClient, HubSpotConfig config) {
        this.feignClient = feignClient;
        this.config = config;
    }

    public AuthToken getAccessToken(String code) {
        Map<String, String> requestBody = Map.of(
                "grant_type", "authorization_code",
                "client_id", config.getClientId(),
                "client_secret", config.getClientSecret(),
                "redirect_uri", config.getRedirectUri(),
                "code", code
        );

        try {
            String formBody = FormDataBuilder.build(requestBody);

            Map<String, Object> response = feignClient.requestToken(
                    "application/x-www-form-urlencoded",
                    formBody
            );

            log.info("Resposta com o token obtida");

            currentToken = new AuthToken(
                    (String) response.get("access_token"),
                    (String) response.get("refresh_token"),
                    (Integer) response.get("expires_in")
            );

            return currentToken;
        } catch (Exception e) {
            log.error("Erro ao trocar o código por um token:", e);
            throw e;

        }
    }

    public String getActiveAccessToken() {
        if (currentToken == null || currentToken.isExpired()) {
            throw new RuntimeException("Token inválido ou expirado. É necessário gerar um novo token.");
        }
        return currentToken.getAccessToken();
    }
}