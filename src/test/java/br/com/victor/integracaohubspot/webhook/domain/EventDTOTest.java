package br.com.victor.integracaohubspot.webhook.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventDTOTest {

    @Test
    void testToStringWithAllFields() {
        EventDTO eventDTO = new EventDTO(
                123L,
                456L,
                789L,
                101112L,
                131415161718L,
                "subscriptionTypeValue",
                1,
                192021L,
                "changeSourceValue",
                "changeFlagValue"
        );

        String expected = "EventDTO{" +
                "appId=123, " +
                "eventId=456, " +
                "subscriptionId=789, " +
                "portalId=101112, " +
                "occurredAt=131415161718, " +
                "subscriptionType='subscriptionTypeValue', " +
                "attemptNumber=1, " +
                "objectId=192021, " +
                "changeSource='changeSourceValue', " +
                "changeFlag='changeFlagValue'" +
                '}';

        String actual = eventDTO.toString();

        assertEquals(expected, actual);
    }

    @Test
    void testToStringWithNullFields() {
        EventDTO eventDTO = new EventDTO();

        String expected = "EventDTO{" +
                "appId=null, " +
                "eventId=null, " +
                "subscriptionId=null, " +
                "portalId=null, " +
                "occurredAt=null, " +
                "subscriptionType='null', " +
                "attemptNumber=null, " +
                "objectId=null, " +
                "changeSource='null', " +
                "changeFlag='null'" +
                '}';

        String actual = eventDTO.toString();

        assertEquals(expected, actual);
    }
}