package br.com.victor.integracaohubspot.authorization.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "hubspotAuthClient", url = "${hubapi.oauth.tokenUrl}")
public interface HubSpotAuthorizationFeignClient {

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    Map<String, Object> requestToken(
            @RequestHeader("Content-Type") String contentType,
            @RequestBody String body);
}