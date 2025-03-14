package br.com.victor.integracaohubspot.contact.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "hubspotContactClient", url = "${hubspot.contact.url}")
public interface HubSpotContactFeignClient {

    @PostMapping
    Map<String, Object> createContact(
            @RequestHeader("authorization") String authorization,
            @RequestBody Map<String, Object> properties
    );
}