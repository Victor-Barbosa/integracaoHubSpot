package br.com.victor.integracaohubspot.authorization.domain;

import java.util.Map;
import java.util.stream.Collectors;

public class FormDataBuilder {

    public static String build(Map<String, String> requestBody) {
        return requestBody.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }
}