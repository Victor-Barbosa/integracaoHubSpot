package br.com.victor.integracaohubspot.contact.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContactDTOTest {

    @Test
    void testToMapWithAllFields() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithEmptyFirstName() {
        String firstName = "";
        String lastName = "Smith";
        String email = "jane.smith@example.com";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithNullFirstName() {
        String firstName = null;
        String lastName = "Brown";
        String email = "michael.brown@example.com";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertNull(result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithEmptyLastName() {
        String firstName = "Emily";
        String lastName = "";
        String email = "emily.jones@example.com";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithNullLastName() {
        String firstName = "Sophia";
        String lastName = null;
        String email = "sophia.lee@example.com";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertNull(result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithNullEmail() {
        String firstName = "Daniel";
        String lastName = "Taylor";
        String email = null;
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertNull(result.get("email"));
    }
}