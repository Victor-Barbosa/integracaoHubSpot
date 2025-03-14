package br.com.victor.integracaohubspot.authorization.domain;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormDataBuilderTest {

    @Test
    void testBuildWithSingleEntry() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("key1", "value1");

        String result = FormDataBuilder.build(requestBody);

        assertEquals("key1=value1", result);
    }

    @Test
    void testBuildWithMultipleEntries() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("key1", "value1");
        requestBody.put("key2", "value2");

        String result = FormDataBuilder.build(requestBody);

        assertEquals("key1=value1&key2=value2", result);
    }

    @Test
    void testBuildWithEmptyMap() {
        Map<String, String> requestBody = new HashMap<>();

        String result = FormDataBuilder.build(requestBody);

        assertEquals("", result);
    }

    @Test
    void testBuildWithSpecialCharacters() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("key@1", "value&1");
        requestBody.put("key=2", "value=2");

        String result = FormDataBuilder.build(requestBody);

        assertEquals("key@1=value&1&key=2=value=2", result);
    }

    @Test
    void testBuildWithNullValue() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("key1", null);

        String result = FormDataBuilder.build(requestBody);

        assertEquals("key1=null", result);
    }
}